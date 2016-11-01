<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <style type="text/css">
        body {
            background-image:
                url('http://cdn.crunchify.com/wp-content/uploads/2013/03/Crunchify.bg_.300.png');
        }
    </style>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Servlet Alumno</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
        <!-- Script que recoge de NewServlet los alumnos SQL-->
        <script>
            $(document).ready(function () {
                $.get('NewServlet', function (responseJson) {
                    var alumnSelect = $('#chooseAlumnos');
                    alumnSelect.find('option').remove();
                    $.each(responseJson, function (key, value) {
                        $('<option>').val(key).text(value[1]).appendTo(alumnSelect);
                    });
                });
            });
        </script>
        <!-- Funciones angular POST que imprime los alumnos y su xml -->
        <script>
            var helloAjaxApp = angular.module("myApp", []);

            helloAjaxApp.controller("myCtrl", ['$scope', '$http', function ($scope, $http) {

                    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";

                    $scope.sendPost = function () {
                        $http({
                            url: 'NewServlet',
                            method: 'POST',
                            data: {
                                'name': $scope.name
                            }
                        }).then(function (response) {
                            console.log(response);
                            $scope.Aname = response.data[0];
                            $scope.asign = response.data[1];
                            $scope.tuto = response.data[2];
                        }, function (response) {
                            //fail case
                            console.log(response);
                            $scope.message = response;
                        });

                    };

                    $scope.sendPostxml = function () {
                        $http({
                            url: 'xmlServlet',
                            method: 'POST',
                            data: {
                                'name': $scope.name
                            }
                        }).then(function (response) {
                            console.log(response);
                            $scope.message = response.data;
                        }, function (response) {
                            //fail case
                            console.log(response);
                            $scope.message = response;
                        });
                    };
                }]);
        </script>
    </head>
    <body ng-app="myApp">
        <div  ng-controller="myCtrl" >
            <form>
                <select ng-model="name" id="chooseAlumnos" ng-change="sendPost(); sendPostxml()" name="chooserName" style="font-size: 25px; margin-left: 80px;">
                </select>
            </form>
            <p style="font-size: 25px; margin-left: 80px;"><strong>{{Aname.name}}</strong></p>
            <p ng-repeat="x in asign" style="font-size: 25px; margin-left: 80px;">{{x}}</p><br>
            <p ng-repeat="x in tuto" style="font-size: 25px; margin-left: 80px;">{{x}}</p><br>
            <pre style="font-size: 15px; margin-left: 80px;">{{message}}</pre>
        </div>
    </body>
</html>