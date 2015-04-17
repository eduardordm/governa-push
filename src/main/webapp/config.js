'use strict';

var CONTENT_TAG = '_embedded';
var HREF_TAG = 'href';
var LINKS_TAG = 'links';
var GETLIST_OP = 'getList';
var GET_OP = 'get';


var envConfig = {
  dev: {
//     url: 'http://localhost:8080/api'
    url: 'api'
  },
  prod: {
    url: 'api'
  }
};

var env = envConfig['dev'];

angular.module('governaPush').config(function(RestangularProvider) {


  RestangularProvider.setBaseUrl(env.url);


  RestangularProvider.setRestangularFields({
    selfLink: '_links.self.href'
  });

  /**
   * Configura resposta HATEOAS em uma lista simples
   * @param  {[type]} data      [description]
   * @param  {[type]} operation [description]
   * @param  {[type]} route     [description]
   * @param  {[type]} url       [description]
   * @param  {[type]} response  [description]
   * @param  {[type]} deferred  [description]
   * @return {[type]}           [description]
   */
  RestangularProvider.setResponseExtractor(function(data, operation, route, url, response, deferred) {
    var
      returnData = data,
      conteudo = {},
      retorno = [],
      key = '',
      path = '',
      recurso = ''
      ;
    if (operation === GETLIST_OP && CONTENT_TAG in data) {
      returnData = data[CONTENT_TAG][route];
      delete data[CONTENT_TAG];

      for (key in data) {
        returnData[key] = data[key];
      }

    }
    if (operation === GET_OP && CONTENT_TAG in data) {
      path = url.substring(url.indexOf('/api/')+5);
      recurso = path.substring(0,path.indexOf('/'));
      conteudo = data[CONTENT_TAG][recurso];

      for (key in data[CONTENT_TAG][recurso]) {
        retorno.push(conteudo[key]);
      }
      delete returnData[CONTENT_TAG];
      returnData = retorno;
    }
    return returnData;
  });

  RestangularProvider.addRequestInterceptor(function(element, operation, what, url) {
    var nomeServico = '', link = '', prop = null, propObj = {};
    if(operation === 'put' && element._links){
      for(link in element._links){
        if(element[link]){
          if(element[link] && element[link].hasOwnProperty('alias')){
            nomeServico = element[link].alias;
          } else {
            nomeServico = link;
          }
          element[link] = RestangularProvider.configuration.baseUrl+'/'+nomeServico+'/'+element[link].id;
        }
      }
    }
    if(operation === 'post'){
      for(prop in element){
        propObj = element[prop];
        if(propObj && typeof propObj === 'object' && propObj.hasOwnProperty('id')){
          if(propObj.tipo && propObj.tipo==='enum'){
            element[prop] = propObj.id;
          } else {
            element[prop] = RestangularProvider.configuration.baseUrl+'/'+prop+'/'+propObj.id;
          }
        }
      }
    }
    /**
     * Caso a entidade nao tenha sido inicializada como um objeto em branco
     * na tela do crud (modelo.orgaoAtendimento = {}), a requisicao e feita
     * com um array, ao inves de um objeto.
     * Aqui estah sendo forcado o envio de um objeto em branco, quando o elemento
     * eh um array vazio
     */
    if(operation === 'post' && _.isArray(element) && element.length===0){
      element = {};
    }
    return element;
  });

  /**
   * Tratamento relacionado aos combos.
   * Para os casos onde o nome da propriedade eh diferente do nome do resource.
   * Exemplo: entidade: horarioAtendimento, propriedade: horarioSegunda
   */
  RestangularProvider.addRequestInterceptor(function(element, operation, what, url) {
    var nomeServico = '';
    if(operation === 'post' || operation === 'put'){
      for(var obj in element){
        if(element[obj] && element[obj].hasOwnProperty('alias')){
          if(element[obj].hasOwnProperty('tipo') && element[obj].tipo==='enum'){
            element[obj] = element[obj].id;
          } else {
            element[obj] = element[obj].self;
          }
        }
      }
    }
    return element;
  });

});
