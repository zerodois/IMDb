/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var VueAPP = null;
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
    VueAPP.$http.get(`${route}?name=${encodeURI(ev.target.value)}`)
        .then(data => update(data, route, $parent))
})

function update(data, route, $parent) {
    let $select = $parent.find('.selectpicker');
    var arr = []
    $select.find("option:selected").each(function(i, item){
        arr.push({ name: item.text, id: item.value })
    })
    console.log(arr)
    VueAPP[`${route}s`] = arr.concat(data.body[`${route}s`])
    setTimeout(function(){
        $parent.find('.no-results').text('Nenhum resultado encontrado');
        $select.selectpicker('refresh');
    }, 500)
}


