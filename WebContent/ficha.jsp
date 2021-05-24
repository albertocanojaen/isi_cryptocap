<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cryptocap.Criptomoneda"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<header>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>CryptoCap - ${criptos.acronimo}</title>
    <link rel="stylesheet" href="/dist/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:300,400,700">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/css/pikaday.min.css">
    <link rel="stylesheet" href="/dist/bootstrap/css/style.css">

    <style>
        .portfolio-block .heading h2 {
            text-transform: capitalize !important;
        }
    </style>
</header>

<body>
    <nav class="navbar navbar-dark navbar-expand-lg fixed-top bg-white portfolio-navbar gradient">
        <div class="container"><img src="/dist/img/small-logo.png"><a class="navbar-brand logo" href="/">&nbsp; cryptocap</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navbarNav"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
           <div class="collapse navbar-collapse" id="navbarNav">
              <ul class="nav navbar-nav ml-auto">
                <li class="nav-item"><a class="nav-link" href="/list"><i class="fas fa-coins"></i> Criptomonedas</a></li>
                <li class="nav-item"><a class="nav-link" href="/compare"><i class="fas fa-exchange-alt"></i> Conversor</a></li>
                <li class="nav-item"><a class="nav-link" href="/market"><i class="fas fa-search-dollar"></i> Mercado</a></li>
                <li class="nav-item"><a class="nav-link" href="/config"><i class="fas fa-wrench"></i> Configuración</a></li>
              </ul>
          </div>
        </div>
    </nav>
    <c:if test="${criptos.acronimo != null}">
        <main class="page cv-page">
            <section class="portfolio-block cv">
                <div class="container">
                    <div class="work-experience group">

                        <div class="heading" style="display: inline !important">
                            <h2 class="text-center text-body">
                                <a href="/list"><i class="fas fa-arrow-left float-left"></i></a>
                                <img class="responsive" src="${criptos.imagen}"/> ${criptos.nombre}
                            </h2>
                            
                        </div>

                        <div class="item">
                            <div class="row">
                                <c:if test="${not empty criptos.precio}">
                                    <div class="col-md-6">
                                        <h3>Precio: </h3> <h4 class="organization">${criptos.precio}$</h4>
                                    </div>
                                </c:if>

                                <div class="col-md-6">
                                    <h3>Capitalizacion: </h3> <h4 class="organization">${criptos.total_market_cap}</h4>
                                </div>

                                <c:if test="${not empty criptos.total_volume_24h}">
                                    <div class="col-md-6">
                                        <h3>Volumen ultimas 24 horas: </h3> <h4 class="organization">${criptos.total_volume_24h}</h4>
                                    </div>
                                </c:if>

                                <div class="col-md-6">
                                    <h3>Volumen ultimos 7 dias: </h3> <h4 class="organization">${criptos.volumenTotal}</h4>
                                </div>
                                
                                <c:if test="${not empty criptos.variacion24}"> 
                                    <div class="col-md-6">
                                        <h3>Cambio ultimas 24 horas: </h3> <h4 class="organization">${criptos.variacion24}</h4>
                                    </div>
                                </c:if>

                                <div class="col-md-6">
                                    <h3>Cambio ultima semana: </h3> <h4 class="organization">${criptos.variacion7}</h4>
                                </div>
                            </div>
                        </div>

                        <p class="text-muted">${criptos.desc}</p>
                        
                    
                        <div class="heading" style="display: inline !important">
                            <h2 class="text-center text-body"><i class="fas fa-history"></i> Historial de precio</h2>
                        </div>

                        <div class="col-md-12">
                            <table class="table">
                                <tr>
                                    <th scope="col">Fecha</th>
                                    <th scope="col">Acrónimo</th>
                                    <th scope="col">Valor</th>
                                </tr>
                                
                                <c:forEach items="${hist}" var="historial">
                                    <tr>
                                        <td>${historial.fecha}</td>
                                        <td>${historial.acronimo}</td>
                                        <td>${historial.valor}$</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        
                       
                    </div>
                    
                </div>
            </section>
        </main>
    </c:if>
    <c:if test="${criptos.acronimo == null}">


    </c:if>
    <footer class="page-footer">
        <div class="container">
            <div class="links"><a href="#">ISI 2021</a><a href="#">Contáctanos</a><a href="#">Github</a></div>
            <div class="social-icons"></div>
        </div>
    </footer>
    <script src="/dist/js/jquery.min.js"></script>
    <script src="/dist/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/pikaday.min.js"></script>
    <script src="/dist/js/script.min.js"></script>
    <script src="https://kit.fontawesome.com/7a8b17dfb3.js" crossorigin="anonymous"></script>
</body>

</html>