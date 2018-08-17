$(document).ready(function () {
    var token = $("#frsc").attr("content");
    getNewRequestsCount(token);
    getAcceptedRequestsCount(token);
});

function getNewRequestsCount(token) {
    var value = $("#new_request").text();
    $.ajax({
        type: "POST",
        url: "/user/request/new/count",
        data: {_csrf: token},
        success: function (data) {
            $("#new_request").text(value + "(" + data +")")
        },
        error: function () {
            window.location = "/error"
        }
    });
}

function getAcceptedRequestsCount(token) {
    var value = $("#accepted_request").text();
    $.ajax({
        type: "POST",
        url: "/user/request/accepted/count",
        data: {_csrf: token},
        success: function (data) {
            $("#accepted_request").text(value + "(" + data +")")
        },
        error: function () {
            window.location = "/error"
        }
    });
}