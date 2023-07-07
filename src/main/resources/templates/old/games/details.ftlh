<#--@model BoardGameLibrary.Data.Models.Game-->

<#--@{-->
<#--ViewBag.Title = "Details";-->
<#--Layout = "~/Views/Shared/_Layout.cshtml";-->
<#--}-->
<#import "../common/page.ftlh" as p/>
<@p.page>
<h2>Details</h2>
<div>
    <h4>Game</h4>
    <dl class="dl-horizontal">
        <dt>Title</dt>
        <dd>${game.title()}</dd>
    </dl>
</div>
<p>
<#--    @Html.ActionLink("Edit", "Edit", new { id = Model.ID }, new { @class = "btn btn-default" })-->
    <a href="/games/old/edit" class="btn btn-default">Edit</a>
</p>
</@p.page>