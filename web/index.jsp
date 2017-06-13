<%-- 
    Document   : index
    Created on : 19/abr/2017, 1:32:57
    Author     : felipe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IMDb</title>
        <%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
        <tag:include />
    </head>
    <body>
        <section class="landing">
            <header class="header">
                <section class="left">
                    <i class="icon-logo"></i>
                </section>
                <section class="right">
                    <a class="white" href="#">Estatísticas</a>
                </section>
            </header>
            <div class="content">
                <form class="has-text-centered" action="/imdb/search" method="GET">
                    <h1 class="title">The Internet Movie Database.</h1>
                    <div class="columns is-gapless">
                        <div class="column is-10">
                            <div class="field">
                                <p class="control">
                                    <input class="input search" autocomplete="off" name="title" type="text" placeholder="">
                                </p>
                           </div>                     
                        </div>
                        <div class="column is-2">
                            <button class="button is-outlined ghost">Search</button>
                        </div>
                    </div>
                </form>
            </div>
        </section>
    </body>
</html>
