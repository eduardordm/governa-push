'use strict';

function MensagemListaCtrl($scope, Restangular) {

  $scope.titulo = 'Mensagens';

  $scope.lista = function () {
    Restangular.all('mensagem').getList().then(function (mensagens) {
      $scope.mensagens = mensagens;
    });
  }

  $scope.gravar = function(mensagem) {
    if(mensagem.id === undefined) {
      Restangular.all('mensagem').post(mensagem).then(function () {
        $scope.lista();
      });
    } else {
      mensagem.put();
    }
  }

  $scope.mostrar = function(mensagem) {
    $scope.mensagem = mensagem;
  }

  $scope.limpar = function() {
    $scope.mensagem = {}
  }

  $scope.limpar();
  $scope.lista();

}

angular
  .module('governaPush')
  .controller('MensagemListaCtrl', MensagemListaCtrl);
