/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var VueAPP = null;
var act = 0;

function setApp (app) {
    VueAPP = app;
}
$(document).on('click', '.btn-group.bootstrap-select button', function(ev) {
    $(ev.target).parents('.btn-group.bootstrap-select').toggleClass('open');
})

$(document).on('keyup', '.btn-group.bootstrap-select.update input', function (ev) {
    if (ev.target.value.length <= 3 || ev.keyCode === 8)
        return false;
    let $parent = $(ev.target).parents('.bootstrap-select');
    $parent.find('.no-results').text('Carregando...');
    let $select = $parent.find('.selectpicker');
    let route = $select.data('update')
    act = Date.now()/1000
    analyze(ev, route, $parent, act)
})

function analyze (ev, route, $parent, timestamp) {
    window.setTimeout(function() {
        if (act != timestamp)
            return false;
        VueAPP.$http.get(`api/${route}?name=${encodeURI(ev.target.value)}`)
            .then(data => update(data, route, $parent))
    }, 2000)
}

function update(data, route, $parent) {
    let $select = $parent.find('.selectpicker');
    var arr = []
    $select.find("option:selected").each(function(i, item){
        arr.push({ name: item.text, id: item.value })
    })
    VueAPP[`${route}s`] = arr.concat(data.body[`${route}s`])
    console.log(data.body[`${route}s`])
    setTimeout(function(){
        $parent.find('.no-results').text('Nenhum resultado encontrado');
        $select.selectpicker('refresh');
    }, 500)
}

