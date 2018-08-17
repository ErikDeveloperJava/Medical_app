$(document).ready(function () {
    var token = $("#frsc").attr("content");
    var commentError = $("#comment_error").attr("content");
    var postId = $("#postId").val();
    var commentText = $("#comments-count").text().split(" ")[1];
    var count = $("#comments-count").text().split(" ")[0];

    $("#comment-form").on("submit", function (event) {
        event.preventDefault();
        var comment = $("#comment").val();
        if (comment == null || comment.length < 4) {
            $("#commentError").text(commentError)
        } else {
            $.ajax({
                type: "POST",
                url: "/post/comment/add",
                data: {_csrf: token, comment: comment, postId: postId},
                success: function (data) {
                    if (data == null) {
                        $("#commentError").text(commentError)
                    } else {
                       var sendDate = new Date(data.sendDate);
                       var day = sendDate.getDate();
                       if(parseInt(day) < 10){
                           day = "0" + day;
                       }
                       var month = sendDate.getMonth() + 1;
                       if(parseInt(month) < 10){
                           month = "0" + month;
                       }
                       var year = sendDate.getFullYear();
                       var hours = sendDate.getHours();
                       var minute = sendDate.getMinutes();
                       var strSenDate = "  " + day + "." + month +"." + year + " " + hours + ":" + minute;
                       var commentBlog = "<div class='col-md-12'>" +
                           "<div class='comment-list'>" +
                           "<h4>" +
                           "<img src='/resources/images/" + data.user.imgUrl + "' class='img-responsive' alt='Image'>" +
                           "<span style='color: #00aae6'>" + data.user.name + " " + data.user.surname + "</span>" +
                           "<span>" + strSenDate + "</span>" +
                           "</h4>" +
                           "<p>" +  data.comment + "</p>" +
                           "</div>" +
                           "</div>";
                       $("#comment-form-blog").before(commentBlog);
                       $("#comments-count").text((parseInt(count) + 1) + " " + commentText);
                       $("#comment").val("");
                    }
                },
                error: function () {
                    window.location = "/error";
                }
            });
        }
    });

    $("#comment").on("input", function () {
        var comment = $(this).val();
        if (comment == null || comment.length < 4) {
            $("#commentError").text(commentError)
        } else {
            $("#commentError").text("");
        }
    })
});