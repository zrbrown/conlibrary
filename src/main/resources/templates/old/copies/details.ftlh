<#--@model BoardGameLibrary.Data.Models.Copy-->

<#--@{-->
<#--ViewBag.Title = "Details";-->
<#--Layout = "~/Views/Shared/_Layout.cshtml";-->
<#--}-->
<#import "../common/page.ftlh" as p/>
<@p.page>
<h2>Details</h2>
<div>
    <h4>Copy</h4>
    <dl class="dl-horizontal">
        <dt>Owner</dt>
        <dd>${copy.ownerName()}</dd>
        <dt>Title</dt>
        <dd>${game.title()}</dd>
        <dt>Library ID</dt>
        <dd>${copy.libraryId()}</dd>
        <dt>Checked out to</dt>
        <dd>${copy.currentCheckout().attendee().name()}</dd>
        <dt>Checked out at</dt>
        <dd>${copy.currentCheckout().start()}</dd>
        <dt>Notes</dt>
        <dd>${copy.notes()}</dd>
    </dl>
</div>
<p>
<#--    @Html.ActionLink("Edit", "Edit", new { id = Model.ID }, new { @class = "btn btn-default" })-->
    <a href="/copies/old/edit" class="btn btn-default">Edit</a>
</p>
</@p.page>