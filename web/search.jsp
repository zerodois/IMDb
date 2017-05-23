<%-- 
    Document   : search
    Created on : 19/mai/2017, 10:59:11
    Author     : felipe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IMDb | <%= request.getAttribute("search") %></title>
        <%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
        <tag:include />
    </head>
    <body>
        <div id="app">
            <!-- INICIO: MODAL  -->
            <div id="movie-viewer" class="modal" :class="{ 'is-active' : active}" v-if="active">
                <div class="modal-background"></div>
                <div class="modal-content">
                    <section class="box">
                      <p class="image">
                        <img :src="'img/movies/' + index + '.jpg'">
                      </p>
                    </section>
                </div>
                <button class="modal-close" @click="setActive(null)"></button>
            </div>
            <!-- FIM: MODAL -->

            <header id="resp-header" class="header has-shadow is-primary blurrable">
                <section class="left">
                    <a href="/imdb"><i class="icon-logo primary"></i></a>
                </section>
                <section class="center search-area">
                    <input class="input is-primary" type="text" value="<%= request.getAttribute("search")%>">
                    <i class="fa fa-search"></i>
                </section>
                <section class="right">
                    <a href="#">Estatísticas</a>
                </section>
            </header>
            <section class="container blurrable">
                <article class="columns" v-for="(x, i) in Math.ceil(movies.length/4)">
                    <div class="column is-10 is-offset-1">
                        <div class="columns">
                            <div class="column is-3" v-for="(movie, index) in movies.slice(i*4, (i*4)+4)">
                                <div class="card">
                                    <div class="card-image pointer" @click="setActive(movie, (index + i*4))">
                                      <figure class="image is-square">
                                        <img :src="'img/movies/' + (index + i*4) + '.jpg'" alt="Image">
                                        <!-- <img src="http://bulma.io/images/placeholders/480x480.png" alt="Image"> -->
                                      </figure>
                                    </div>
                                    <div class="movie-subtitle has-text-centered">{{ movie.name }} ({{ movie.year }})</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </article>
            </section>            
        </div>
    </body>
    <tag:scripts />
    <script>
        var data = [
            { name: "Star Wars: o despertar da força", year: "2015" },
            { name: "Rogue One: Uma História de Star Wars", year: "2016" },
            { name: "Star Wars: Episódio VIII - Os Últimos Jedi", year: "2017" },
            { name: "Star Wars: Episódio IV - Uma Nova Esperança", year: "1977" },
            { name: "Star Wars: Episódio V - O Império Contra-Ataca", year: "1980" },
            { name: "Star Wars: Episódio VI - O retorno de Jedi", year: "1983" },
            { name: "Star Wars: Episódio I - A Ameaça Fantasma", year: "1999" },
            { name: "Star Wars: Episódio II - O Ataque dos Clones", year: "2002" },
            { name: "Star Wars: Episódio III - A Vingança dos Sith", year: "2005" },
            { name: "Lego Star Wars: The Empire Strikes Out", year: "2012" },
        ];
        var app = new Vue({
            el: '#app',
            data: {
                movies: data,
                active: null,
                index: -1
            },
            methods: {
                setActive (active, index) {
                    this.active = active;
                    this.index = index;
                }
            }
        });
    </script>
</html>
