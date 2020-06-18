$(function(){


    $("#previous").click(function(){
        let currentPage = $("#currentPage").text();
        if(currentPage === "1"){
            return false;
        }
        currentPage = parseInt(currentPage);
        currentPage--;
        window.location.href = "/?page="+currentPage;
    });

    $("#next").click(function(){
        let currentPage = $("#currentPage").text();
        const pages = $("#pages").text();
        if(currentPage === pages){
            return false;
        }
        currentPage = parseInt(currentPage);
        currentPage++;
        window.location.href = "/?page="+currentPage;
    });


});