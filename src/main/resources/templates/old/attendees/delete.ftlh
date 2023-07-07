<#--@model BoardGameLibrary.Data.Models.Attendee-->

<#--@{-->
<#--ViewBag.Title = "Delete";-->
<#--Layout = "~/Views/Shared/_Layout.cshtml";-->
<#--}-->
<#import "../common/page.ftlh" as p/>
<@p.page>
<h2>Delete</h2>
<h3>Are you sure you want to delete this?</h3>
<div>
    <h4>Attendee</h4>
    <dl class="dl-horizontal">
        <dt>Name</dt>
        <dd>${attendee.name()}</dd>
        <dt>Badge #</dt>
        <dd>${attendee.badgeId()}</dd>

    </dl>

<#--    @using (Html.BeginForm()) {-->
<#--    @Html.AntiForgeryToken()-->
<#--    <div class="form-actions no-color">-->
<#--        <input type="submit" value="Delete" class="btn btn-danger" />-->
<#--    </div>-->
<#--    }-->
    <form>
        <div class="form-actions no-color">
            <input type="submit" value="Delete" class="btn btn-danger" />
        </div>
    </form>
</div>
</@p.page>