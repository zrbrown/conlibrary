<#--@model BoardGameLibrary.Data.Models.Attendee-->

<#--@{-->
<#--ViewBag.Title = "Edit";-->
<#--Layout = "~/Views/Shared/_Layout.cshtml";-->
<#--}-->
<#import "../common/page.ftlh" as p/>
<@p.page>
<h2>Edit</h2>
<#--@using (Html.BeginForm())-->
<#--{-->
<#--@Html.AntiForgeryToken()-->

<div class="form-horizontal">
    <h4>Attendee</h4>
<#--    @Html.ValidationSummary(true, "", new { @class = "text-danger" })-->
<#--    @Html.HiddenFor(model => model.ID)-->

    <div class="form-group">
<#--        @Html.LabelFor(model => model.Name, htmlAttributes: new { @class = "control-label col-md-2" })-->
        <label class="control-label col-md-2">Name</label>
        <div class="col-md-10">
<#--            @Html.EditorFor(model => model.Name, new { htmlAttributes = new { @class = "form-control" } })-->
            <input type="text" class="form-control"/>
<#--            @Html.ValidationMessageFor(model => model.Name, "", new { @class = "text-danger" })-->
        </div>
    </div>

    <div class="form-group">
<#--        @Html.LabelFor(model => model.BadgeID, htmlAttributes: new { @class = "control-label col-md-2" })-->
        <label class="control-label col-md-2">Badge #</label>
        <div class="col-md-10">
<#--            @Html.EditorFor(model => model.BadgeID, new { htmlAttributes = new { @class = "form-control" } })-->
            <input type="text" class="form-control"/>
<#--            @Html.ValidationMessageFor(model => model.BadgeID, "", new { @class = "text-danger" })-->
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
            <input type="submit" value="Save" class="btn btn-success" />
        </div>
    </div>
</div>
<#--}-->
</@p.page>