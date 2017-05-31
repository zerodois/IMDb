<%-- 
    Document   : search
    Created on : 19/mai/2017, 10:59:11
    Author     : felipe
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.Movie"%>
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
                        <img :src="active.photo">
                      </p>
                    </section>
                </div>
                <button class="modal-close" @click="setActive(null)"></button>
            </div>
            <!-- FIM: MODAL -->

            <header id="resp-header" class="header has-shadow is-primary blurrable">
                <section class="left">
                    <a href="/imdb"><i class="icon-logo white"></i></a>
                </section>
                <form action="/imdb/search" method="GET">
                    <section class="center search-area">
                        <input class="input is-primary" type="text" name="term" value="<%= request.getAttribute("search")%>">
                        <i class="fa fa-search"></i>
                    </section>
                </form>
                <section class="right">
                    <a href="#" class="white">Estatísticas</a>
                </section>
            </header>
            <section class="container blurrable">
                <article class="columns">
                    <div class="column is-5 is-offset-1">
                        <%= request.getAttribute("total") %> Resultados encontrados
                    </div>
                </article>
                <article class="columns" v-for="(x, i) in Math.ceil(movies.length/4)">
                    <div class="column is-10 is-offset-1">
                        <div class="columns">
                            <div class="column is-3" v-for="(movie, index) in movies.slice(i*4, (i*4)+4)">
                                <div class="card">
                                    <div class="card-image pointer" @click="setActive(movie, (index + i*4))">
                                      <figure class="image is-square">
                                        <!-- <img :src="'img/movies/' + (index + i*4) + '.jpg'" alt="Image"> -->
                                        <img :src="movie.photo" alt="Image">
                                      </figure>
                                    </div>
                                    <div class="movie-subtitle has-text-centered">{{ movie.title }} ({{ movie.year }})</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </article>
                <article class="columns" style="margin-bottom: 20px;">
                    <% int r = (Integer) request.getAttribute("total"); %>
                    <% int rp = (Integer) request.getAttribute("results_per_page"); %>
                    <% if (r > rp) { %>
                    <div class="is-12 has-text-centered column">
                        <nav class="pagination is-centered">
                            <ul class="pagination-list">
                                <% int pages = (int) Math.ceil(r/rp); %>
                                <% for (int i=0; i <= pages; i++) { %>
                                    <li><a class="pagination-link <%= i==(Integer)request.getAttribute("page") ? "is-current" : "" %> " href="search?term=<%= request.getAttribute("term") %>&page=<%= i %>"><%= i+1 %></a></li>
                                <% } %>
                            </ul>
                          </nav>
                    </div>
                    <% } %>
                </article>
            </section>            
        </div>
    </body>
    <tag:scripts />
    <script>
        <% ArrayList<Movie> m = (ArrayList<Movie>)request.getAttribute("movies"); %>
        var data = [];
        <% for (Movie item : m) { %>
            data.push({ title: `<%= item.getTitle().replaceAll("\\([0-9]+\\)", "") %>`, year: '<%= item.getYear() %>', photo: 'img/movies/default.jpg' });
        <% } %>
            
        function format (json) {
            if (json.total_results > 0 && json.results[0].poster_path)
                return 'http://image.tmdb.org/t/p/w500' + json.results[0].poster_path;
        }
    
        function loadAssets () {
            var self = this;
            this.movies.forEach((movie, index) => {
                ///*
                var key = '14ec215fa352ace6de70d93ff09bcf04';
                var title = movie.title.trim().replace('"', "").toLowerCase()//.replace(/\ /g, "%20");
                var url = 'https://api.themoviedb.org/3/search/multi?include_adult=true&query=' + escape(title) + '&api_key=' + key;
                self.$http.get(url)
                    .then(resp => self.movies[index].photo = format(resp.body) || self.movies[index].photo)
                    .catch(err => console.log('Error: ', err))
                //*/
            });
        }
        
        // Vue.http.headers.common['Access-Control-Allow-Origin'] = '*'
        // Vue.http.headers.common['Access-Control-Request-Method'] = '*'
        
        var app = new Vue({
            el: '#app',
            data: {
                movies: data,
                active: null,
                index: -1
            },
            created: loadAssets,
            methods: {
                setActive (active, index) {
                    this.active = active;
                    this.index = index;
                }
            }
        });
    </script>
</html>
