<%-- 
    Document   : stats
    Created on : 18/jun/2017, 17:07:35
    Author     : felipe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IMDb | Estatísticas</title>
        <%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
        <tag:include />
    </head>
    <body id="stats">
        <div id="app">
            <header class="header">
                <section class="left">
                    <a href="/imdb">
                        <i class="icon-logo primary"></i>
                    </a>
                </section>
                <section class="right">
                    <i class="fa fa-cog primary"></i>
                </section>
            </header>

            <!-- prepare a DOM container with width and height -->
            <section class="container">
                <div class="columns content transparent">
                    <article class="column is-12">
                        <div style="width: 900px; margin: auto;">
                            Abaixo encontra-se um gráfico contendo um ranking com os 100 pares atores/diretores que mais trabalharam em conjunto, seguido da tabela contendo todos os pares que trabalharam juntos mais que <%= request.getAttribute("X") %> vezes :)
                        </div>
                        <div id="graph" style="width: 1000px; height:500px;"></div>
                    </article>
                </div>
                <div class="columns">
                    <article class="is-10 is-offset-1 column">
                        <table class="table is-striped no-bottom">
                            <thead>
                              <tr>
                                <th>Nome do ator</th>
                                <th>Nome do diretor</th>
                                <th>Trabalhos realizados em dupla</th>
                              </tr>
                            </thead>
                            <tfoot>
                              <tr>
                                <th>Nome do ator</th>
                                <th>Nome do diretor</th>
                                <th>Trabalhos realizados em dupla</th>
                              </tr>
                            </tfoot>
                            <tbody>
                              <tr v-for="item in ranking">
                                <td>{{ item.actor.name }}</td>
                                <td>{{ item.director.name }}</td>
                                <td>{{ item.amount }}</td>
                              </tr>
                            </tbody>
                        </table>
                    </article>
                </div>
            </section>
        </div>
    </body>
    <tag:scripts />
    <!-- DATA -->
    <script>
        let tmp = <%= request.getAttribute("list") %>;
        var ranking = tmp.rankings;
    </script>
    <!-- Vue APP -->
    <script>
        var app = new Vue({
            el: '#app',
            created () {
                let self = this
                document.addEventListener('scroll', function() {
                    let win = $(window)
                    if ($(document).height() - win.height() == win.scrollTop() && self.page > 0) {
                        self.$http.get('api/ranking?X=<%= request.getAttribute("X") %>&page=' + self.page)
                            .then(json => {
                                let arr = json.body.rankings
                                if (arr.length === 0)
                                    return self.page = -1
                                self.page += 1
                                self.ranking = self.ranking.concat(arr)
                            })
                    }
                });
            },
            data () {
                return {
                    ranking,
                    page: 2
                }
            }
        })
    </script>
    
    <!-- Gráfico -->
    <script type="text/javascript">        
        // based on prepared DOM, initialize echarts instance
        var myChart = echarts.init(document.getElementById('graph'));

        // specify chart configuration item and data
        var option = {
            color: ['#00D1B2'],
            title: {
                text: 'Gráfico de pares',
                show: false
            },
            xAxis: {
                data: ranking.map(item => item.actor.name + ' <> ' + item.director.name)
            },
            yAxis: {},
            series: [{
                name: 'Trabalhos realizados em conjunto',
                type: 'bar',
                data: ranking.map(item => item.amount)
            }],
            dataZoom: [
                {
                    show: true,
                    start: 0,
                    end: 100,
                    left: '80',
                    right: '100',
                    bottom: '0'
                }
            ],
            tooltip : {         // Option config. Can be overwrited by series or data
                trigger: 'axis',
                //show: true,   //default true
                showDelay: 0,
                hideDelay: 50,
                transitionDuration:0,
                backgroundColor : 'rgba(0, 0, 0, 0.7)',
                borderColor : 'aqua',
                borderRadius : 3,
                borderWidth: 0,
                padding: 10,    // [5, 10, 15, 20]
                position : function(p) {
                    // 位置回调
                    // console.log && console.log(p);
                    return [p[0] + 10, p[1] - 10];
                },
                textStyle : {
                    color: 'white',
                    decoration: 'none',
                    fontFamily: 'Verdana, sans-serif',
                    fontSize: 12
                },
                formatter: function (params, ticket, callback) {
                    var res = params[0].name;
                    for (var i = 0, l = params.length; i < l; i++)
                        res += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
                    //callback(ticket, res);
                    return res;
                }
                //formatter: "Template formatter: <br/>{b}<br/>{a}:{c}<br/>{a1}:{c1}"
            }
        };

        // use configuration item and data specified to show chart
        myChart.setOption(option);
    </script>

</html>
