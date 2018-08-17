$(document).ready(function () {
    var token = $("#frsc").attr("content");

    getDepartments(token);
});

function getDepartments(token) {
    $.ajax({
        type: "POST",
        url: "/departments",
        data: {_csrf: token},
        success: function (data) {
            $("#dp_").append(data);
        },
        error: function () {
            window.location = "/error"
        }
    });
}