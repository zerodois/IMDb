<%-- 
    Document   : search
    Created on : 19/mai/2017, 10:59:11
    Author     : felipe
--%>

<%@page import="model.Actor"%>
<%@page import="model.Director"%>
<%@page import="model.Search"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.Movie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="app">
    <head>
        <meta http-equiv="Content-Typeinitial" content="text/html; charset=UTF-8">
        <% Search bean = (Search)request.getAttribute("search"); %>
        <% String busca = bean.getTitle() != null ? bean.getTitle() : ""; %>
        <title>IMDb | <%= busca %></title>
        <%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
        <tag:include />
    </head>
    <body class="relative app">
        <div id="app" :class="{ 'hidden-scroll' : active }">
            <!-- INICIO: MODAL  -->
            <div id="movie-viewer" class="modal" :class="{ 'is-active' : active }" v-if="active">
                <div class="modal-background"></div>
                <span class="nav-button right" v-show="index < movies.length - 1">
                    <i class="fa fa-chevron-right fa-2x fa-fw pointer" @click="setActive(movies[index + 1], index + 1)" aria-hidden="true"></i>
                </span>
                <span class="nav-button left" v-show="index > 0">
                    <i class="fa fa-chevron-left fa-2x fa-fw pointer" @click="setActive(movies[index - 1], index - 1)" aria-hidden="true"></i>
                </span>

                <div class="modal-content content transparent">
                    <section class="box">
                        <div id="loading" v-show="loading">
                            <div class="sk-folding-cube">
                                <div class="sk-cube1 sk-cube"></div>
                                <div class="sk-cube2 sk-cube"></div>
                                <div class="sk-cube4 sk-cube"></div>
                                <div class="sk-cube3 sk-cube"></div>
                            </div>
                        </div>
                        <div class="overflow" v-show="!loading">
                          <p class="image float-left">
                            <img :src="active.photo">
                          </p>
                          <div class="film-content content float-left">
                              <h4 class="medium no-bottom">{{ active.title }}</h4>
                              <div v-if="active.data">
                                    <label for="">Linguagens: </label><span v-for="l in active.data.languages">{{ l.name }}; </span><br>
                                    <label for="">Gêneros: </label><span v-for="g in active.data.genres">{{ g.name }}; </span><br>
                                    <div v-if="active.data.directors.length > 1" class="ident"><label>Principais diretores: </label><span v-for="i in Math.min(5, active.data.directors.length)">{{ active.data.directors[i-1].name }}; </span></div>
                                    <div v-if="active.data.directors.length == 1"><label>Diretor: </label><span>{{ active.data.directors[0].name }}; </span></div>
                                    <div v-if="active.data.actors.length > 1" class="ident"><label>Principais atores: </label><span v-for="i in Math.min(5, active.data.actors.length)">{{ active.data.actors[i-1].name }}; </span><br></div>
                                    <div v-if="active.data.actors.length == 1" class="ident"><label>Principal ator: </label><span>{{ active.data.actors[0].name }}; </span><br></div>
                                    <a @click="advanced = !advanced">{{ advanced ? 'Ocultar' : 'Ver' }} elenco e direção completos</a>
                              </div>
                          </div>
                        </div>
                        <div v-show="advanced">
                            <h3 class="medium no-margin no-bottom" style="margin-left: 10px !important">Diretores</h3>
                            <table class="table is-striped">
                                <thead>
                                  <tr>
                                    <th>Nome</th>
                                    <th>Informação adicional</th>
                                  </tr>
                                </thead>
                                <tfoot v-if="active.data.directors.length > 10">
                                  <tr>
                                    <th>Nome</th>
                                    <th>Informação adicional</th>
                                  </tr>
                                </tfoot>
                                <tbody>
                                  <tr v-for="director in active.data.directors">
                                    <td>{{ director.name }}</td>
                                    <td>{{ director.addition || '--' }}</td>
                                  </tr>
                                </tbody>
                            </table>

                            <h3 class="medium no-margin no-bottom" style="margin-left: 10px !important">Atores</h3>
                            <table class="table is-striped no-bottom">
                                <thead>
                                  <tr>
                                    <th>Nome</th>
                                    <th>Personagem</th>
                                  </tr>
                                </thead>
                                <tfoot v-if="active.data.directors.length > 50">
                                  <tr>
                                    <th>Nome</th>
                                    <th>Personagem</th>
                                  </tr>
                                </tfoot>
                                <tbody>
                                  <tr v-for="actor in active.data.actors">
                                    <td>{{ actor.name }}</td>
                                    <td>{{ actor.character }}</td>
                                  </tr>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>
                <button class="modal-close" @click="close()"></button>
            </div>
            <!-- FIM: MODAL -->

            <header id="resp-header" class="header has-shadow is-primary blurrable">
                <section class="left">
                    <a href="/imdb"><i class="icon-logo white"></i></a>
                </section>
                <form action="/imdb/search" method="GET">
                    <section class="center search-area relative is-active">
                        <p class="control has-icons-left has-icons-right">
                            <input name="title" class="input is-primary no-padding-top" value="<%= busca %>" placeholder="Título do filme" type="text" placeholder="Text input">
                            <span class="icon is-small is-left">
                              <i class="fa fa-search"></i>
                            </span>
                        </p>
                        <i class="fa fa-caret-down pointer" @click="advanced = !advanced"></i>
                        <div class="advanced-search" v-show="advanced">
                            <hr>
                            <section class="columns no-margin">
                                <article class="column is-4">Ano de lançamento: </article>
                                <article class="column is-8">
                                    <input name="year" v-model="year" @blur="year = Math.min(2017, Math.max(1800, year))" value="<%= bean.getYear() %>" type="number" placeholder="Ex.: 2004" class="input flat">
                                </article>
                            </section>
                            <section class="columns no-margin">
                                <article class="column is-4">Línguagem: </article>
                                <article class="column is-8">
                                    <select data-style="s-1" v-model="lang" name="language" title="Nenhuma linguagem selecionada" data-none-results-text="Nenhum resultado encontrado" class="selectpicker" data-live-search="true">
                                        <option value=""  <%= bean.getLanguage() == null || bean.getLanguage() == "" ? "selected" : "" %>>All languages</option>
                                        <% if (bean.getLanguage() != null) { %>
                                            <option value="<%= bean.getLanguage()%>" selected=""><%= bean.getLanguage()%></option>
                                        <% } %>
                                        <option v-for="lang in langs" :value="lang.name">{{ lang.name }}</option>
                                    </select>
                                </article>
                            </section>
                            <section class="columns no-margin">
                                <article class="column is-4">Gênero: </article>
                                <article class="column is-8">
                                    <select data-style="s-2" v-model="genre" name="genre" title="Nenhum gênero selecionado" data-none-results-text="Nenhum resultado encontrado" class="selectpicker" data-live-search="true">
                                        <option value=""  <%= bean.getGenre() == null || bean.getGenre() == "" ? "selected" : "" %>>All genres</option>
                                        <% if (bean.getGenre() != null) { %>
                                            <option value="<%= bean.getGenre() %>" selected=""><%= bean.getGenre() %></option>
                                        <% } %>
                                        <option v-for="genre in genres" :value="genre.name">{{ genre.name }}</option>
                                    </select>
                                </article>
                            </section>
                            <section class="columns no-margin">
                                <article class="column is-4">Categoria: </article>
                                <article class="column is-8">
                                    <span class="select search is-fullwidth">
                                        <select name="category" v-model="category">
                                          <option value="M">Movie</option>
                                          <option value="T">TV Show</option>
                                          <option value="E">Episode</option>
                                          <option value="V">Video</option>
                                          <option value="G">Game</option>
                                        </select>
                                    </span>
                                </article>
                            </section>
                            <section class="columns no-margin">
                                <article class="column is-4">Atores envolvidos: </article>
                                <article class="column is-8">
                                    <select data-multiple-separator=" | " v-model="actorsAct" name="actors" title="Nenhum ator selecionado" data-update="actor" data-none-results-text="Nenhum resultado encontrado" data-style="update s-3" class="selectpicker" multiple data-live-search="true">
                                        <option v-for="actor in actors" :value="actor.id">{{ actor.name }}</option>
                                    </select>
                                </article>
                            </section>
                            <section class="columns no-margin">
                                <article class="column is-4">Diretores participantes: </article>
                                <article class="column is-8">
                                    <select data-multiple-separator=" | " v-model="directorsAct" name="directors" title="Nenhum diretor selecionado" data-update="director" data-none-results-text="Nenhum resultado encontrado" data-style="update s-4" class="selectpicker" multiple data-live-search="true">
                                        <option v-for="director in directors" :value="director.id">{{ director.name }}</option>
                                    </select>
                                </article>
                            </section>
                            <section class="columns has-text-right no-margin">
                                <article class="column is-11">
                                    <button type="button" class="button is-link" @click="advanced = false">Cancelar</button>
                                    <button type="submit" class="button is-primary">Pesquisar</button>
                                </article>
                            </section>
                        </div>
                    </section>
                </form>
                <section class="right">
                    <a href="stats" class="white">Estatísticas</a>
                </section>
            </header>
            <section class="container blurrable">
                <article class="columns">
                    <% if ((Integer) request.getAttribute("total") > 0) { %>
                    <div class="column is-5 is-offset-1">
                        <%= request.getAttribute("total") %> Resultados encontrados
                    </div>
                    <% } else { %>
                    <div class="column is-12 has-text-centered">
                        <% boolean empty = Boolean.parseBoolean(request.getAttribute("empty").toString()); %>
                        <figure class="image empty-search"><img src="img/<%= !empty ? "ue" : "empty" %>.svg" alt=""></figure>
                    </div>
                    <% } %>
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
                                <%
                                    String link = "title=" + bean.getTitle();
                                    if (bean.getYear() != null)
                                        link += "&year=" + bean.getYear();
                                    if (bean.getGenre()!= null)
                                        link += "&genre=" + bean.getGenre();
                                    if (bean.getLanguage()!= null)
                                        link += "&language=" + bean.getLanguage();
                                    if (bean.getCategory() != null)                                    
                                        link += "&category=" + bean.getCategory();
                                    String[] d = bean.getDirectors();
                                    String[] a = bean.getActors();
                                    if (d != null)
                                        for (String s : d)
                                            link += "&directors=" + s;
                                    if (a != null)
                                        for (String s : a)
                                            link += "&actors=" + s;
                                %>
                                <% int current = (Integer)request.getAttribute("page"); %>
                                <% if (current >= 3) { %>
                                    <li><a class="pagination-link" href="search?<%= link %>&page=0">1</a></li>
                                    ...
                                <% } %>

                                <% for (int i=Math.max(current-2, 0); i <= Math.min( Math.max(4, current + 2), pages); i++) { %>
                                    <li><a class="pagination-link <%= i==current ? "is-current" : "" %> " href="search?<%= link %>&page=<%= i %>"><%= i+1 %></a></li>
                                <% } %>
                                <% if (pages > 5 && pages-current >= 3) { %>
                                    ...
                                    <li><a class="pagination-link" href="search?<%= link %>&page=<%= pages %>"><%= pages+1 %></a></li>
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
            data.push({ data: { actors: [], directors: [] }, id: <%= item.getId() %>, title: `<%= item.getTitle().replaceAll("\\([0-9|?]+\\)", "") %>`, year: '<%= item.getYear() %>', photo: 'img/movies/default.jpg' });
        <% } %>

        <% ArrayList<Director> d = (ArrayList<Director>)request.getAttribute("directors"); %>
        var directors = [];
        var directorsAct = [];
        <% for (Director item : d) { %>
            directors.push({ name: `<%= item.getName() %>`, id: '<%= item.getId()%>'});
            directorsAct.push(<%= item.getId()%>);
        <% } %>
            
        <% ArrayList<Actor> a = (ArrayList<Actor>)request.getAttribute("actors"); %>
        var actors = [];
        var actorsAct = [];
        <% for (Actor item : a) { %>
            actors.push({ name: `<%= item.getName() %>`, id: '<%= item.getId()%>'});
            actorsAct.push(<%= item.getId()%>);
        <% } %>
    
        function format (json) {
            if (json.total_results > 0 && json.results[0].poster_path)
                return 'http://image.tmdb.org/t/p/w500' + json.results[0].poster_path;
        }
    
        function loadAssets () {
            var self = this;
            this.$http.get('api/genre').then(data => {
                self.genre = '<%= bean.getGenre() != null ? bean.getGenre() : "" %>'
                self.genres = data.body.genres.filter(item => item.name != self.genre)
                if (self.category == 'null')
                    self.category = 'M'
                setTimeout(()=>{
                    $('.selectpicker').selectpicker('refresh');
                }, 500)
            })
            this.$http.get('api/language').then(data => {
                self.lang = '<%= bean.getLanguage() != null ? bean.getLanguage() : "" %>'
                self.langs = data.body.languages.filter(item => item.name != self.lang)
            })
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
        
        function setActive (active, index) {
            //if (this.active)
                //this.active.data = null;
            var self = this
            this.loading = true
            this.advanced = false
            this.index = index;
            self.active = active;
            if (!active) {
                this.active = active;
                return false;
            }
            this.$http.get('api/load?id=' + active.id)
                .then(json => {
                    active.data = json.body.data;
                    self.active = active;
                    self.loading = false;
                });
        }
        
        function close() {
            this.index = null
            this.active = null
            this.advanced = false
        }
        
        var app = new Vue({
            el: '#app',
            data: {
                movies: data,
                langs: [],
                lang: 0,
                genres: [],
                year: '',
                genre: '<%= bean.getGenre() %>',
                category: '<%= bean.getCategory() %>',
                actors,
                actorsAct,
                directors,
                directorsAct,
                active: null,
                index: -1,
                loading: true,
                advanced: false
            },
            created: loadAssets,
            methods: {
                setActive,
                close
            }
        });
        
        setApp(app);
    </script>
</html>
