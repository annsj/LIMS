// Please see documentation at https://docs.microsoft.com/aspnet/core/client-side/bundling-and-minification
// for details on configuring this project to bundle and minify static web assets.

// Write your JavaScript code.


//https://stackoverflow.com/questions/16894683/how-to-print-html-content-on-click-of-a-button-but-not-the-page
function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
}


//https://www.askingbox.com/tutorial/jquery-disable-submit-button-if-no-checkbox-is-selected
// $ = getElementById
$(document).ready(function () {

    //$('#startElisa').attr("disabled", true);

    $('.selectId').change(function () {
        $('#startElisa').attr('disabled', $('.selectId:checked').length == 0);
    });

});