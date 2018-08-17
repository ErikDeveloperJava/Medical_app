$(document).ready(function () {
    var token = $("#frsc").attr("content");

    getNewRegisteredUserCount(token);
    getAcceptedRegisteredUserCount(token);
    getUnconfirmedRegisteredUserCount(token);
});

function getNewRegisteredUserCount (token) {
    var value = $("#new_registered").text();
    $.ajax({
       type: "POST",
       url: "/doctor/users/new/count",
       data: {_csrf: token},
       success: function (data) {
           $("#new_registered").text(value + "(" + data +")");
       },
       error: function () {
           window.location = "/error"
       }
    });
}

function getAcceptedRegisteredUserCount (token) {
    var value = $("#accepted_registered").text();
    $.ajax({
       type: "POST",
       url: "/doctor/users/accepted/count",
       data: {_csrf: token},
       success: function (data) {
           $("#accepted_registered").text(value + "(" + data +")");
       },
       error: function () {
           window.location = "/error"
       }
    });
};

function getUnconfirmedRegisteredUserCount(token) {
    var value = $("#unconfirmed_registered").text();
    $.ajax({
        type: "POST",
        url: "/doctor/users/unconfirmed/count",
        data: {_csrf: token},
        success: function (data) {
            $("#unconfirmed_registered").text(value + "(" + data +")");
        },
        error: function () {
            window.location = "/error"
        }
    })
}