<#--@model BoardGameLibrary.Data.Models.Attendee-->

<#--@{-->
<#--ViewBag.Title = "Details";-->
<#--Layout = "~/Views/Shared/_Layout.cshtml";-->
<#--}-->
<#import "../common/page.ftlh" as p/>
<@p.page>
<h2>Details</h2>
<div>
    <h4>Attendee</h4>
    <dl class="dl-horizontal">
        <dt>Name</dt>
        <dd>${attendee.name()}</dd>
        <dt>Badge #</dt>
        <dd>${attendee.badgeId()}</dd>
    </dl>
</div>
<p>
<#--    @Html.ActionLink("Edit", "Edit", new { id = Model.ID }, new { @class = "btn btn-default" })-->
    <a href="/attendees/old/edit" class="btn btn-default">Edit</a>
</p>
</@p.page>