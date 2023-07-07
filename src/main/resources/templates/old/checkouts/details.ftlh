<#--@model BoardGameLibrary.Data.Models.Checkout-->

<#--@{-->
<#--ViewBag.Title = "Details";-->
<#--Layout = "~/Views/Shared/_Layout.cshtml";-->
<#--}-->
<#import "../common/page.ftlh" as p/>
<@p.page>
<h2>Details</h2>
<div>
    <h4>Checkout</h4>
    <dl class="dl-horizontal">
        <dt>Checked out at</dt>
        <dd>${checkout.start()}</dd>
        <dt>Checked in at</dt>
        <dd>${checkout.end()}</dd>

    </dl>
</div>
<p>
<#--    @Html.ActionLink("Edit", "Edit", new { id = Model.ID }) |-->
<#--    @Html.ActionLink("Back to List", "Index")-->
    <a href="">Edit</a>
    <a href="">Back To List</a>
</p>
</@p.page>