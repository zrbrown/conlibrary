<#--@using PagedList.Mvc;-->
<#--@model AttendeeIndexViewModel-->
<#--@using BoardGameLibrary.Models;-->
<#--@{-->
<#--ViewBag.Title = "Index";-->
<#--Layout = "~/Views/Shared/_Layout.cshtml";-->
<#--}-->
<#import "../common/page.ftlh" as p/>
<@p.page>
<h2>Attendees</h2>
<#--@{-->
<#--ErrorList errorList = (ErrorList)ViewBag.Errors;-->
<#--if (errorList != null && errorList.Errors.Any())-->
<#--{-->
<#--<div class="alert alert-error">-->
<#--    <a class="close" data-dismiss="alert">×</a>-->
<#--    <strong>Errors</strong>-->
<#--    <ul>-->
<#--        @foreach (var err in errorList.Errors)-->
<#--        {-->
<#--        <li>@err</li>-->
<#--        }-->
<#--    </ul>-->
<#--</div>-->
<#--}-->
<#--}-->
<div class="row">
    <div class="col-md-1">
<#--        @Html.ActionLink("Create New", "Create", null, new { @class = "btn btn-primary" })-->
        <a href="/attendees/old/create" class="btn btn-primary">Create New</a>
    </div>
    <div class="col-md-offset-1 col-md-4">
<#--        @using (Html.BeginForm("Index", "Attendees", FormMethod.Get, new { @class = "form-inline" }))-->
<#--        {-->
<#--        <div class="row">-->
<#--            @Html.TextBox("SearchString", ViewBag.CurrentFilter as string, new { @class = "form-control text-box col-md-1", placeholder = "Name or Badge ID" })-->
<#--            <input type="submit" value="Search" class="btn btn-primary col-md-3" />-->
<#--        </div>-->
<#--        }-->
        <form>
            <div class="row">
                <input type="text" placeholder="Name or Badge ID" class="form-control text-box col-md-1"/>
                <input type="submit" value="Search" class="btn btn-primary col-md-3" />
            </div>
        </form>
    </div>

<#--    @if (User.IsInRole("AdminUI-Admin"))-->
<#--    {-->
<#--    <div class="col-md-offset-1 col-md-5">-->
<#--        @using (Html.BeginForm("Import", "Attendees", FormMethod.Post, new { enctype = "multipart/form-data" }))-->
<#--        {-->
<#--        <div class="form-inline">-->
<#--            <div class="form-group width50pct">-->
<#--                <input type="file" class="filestyle" data-buttontext=" Browse..." data-buttonname="btn-primary" data-buttonbefore="true" name="File" />-->
<#--            </div>-->
<#--            <input type="submit" name="submit" class="btn btn-success" value="Import" />-->
<#--        </div>-->
<#--        }-->
<#--    </div>-->
<#--    }-->

<#--    if is admin-->
    <#if true>
    <div class="col-md-offset-1 col-md-5">
        <form>
            <div class="form-inline">
                <div class="form-group width50pct">
                    <input type="file" class="filestyle" data-buttontext=" Browse..." data-buttonname="btn-primary" data-buttonbefore="true" name="File" />
                </div>
                <input type="submit" name="submit" class="btn btn-success" value="Import" />
            </div>
        </form>
    </div>
    </#if>
</div>
<br />
<table class="table">
    <tr>
        <th>
<#--            @Html.DisplayNameFor(model => model.Attendees[0].Name)-->
            Name
        </th>
        <th>
<#--            @Html.DisplayNameFor(model => model.Attendees[0].BadgeID)-->
            Badge ID
        </th>
        <th></th>
    </tr>
<#--    @foreach (var item in Model.Attendees) {-->
<#--    <tr>-->
<#--        <td>-->
<#--            @Html.DisplayFor(modelItem => item.Name)-->
<#--        </td>-->
<#--        <td>-->
<#--            @Html.DisplayFor(modelItem => item.BadgeID)-->
<#--        </td>-->
<#--        <td>-->
<#--            @Html.ActionLink("Edit", "Edit", new { id = item.ID }, new { @class = "btn btn-default btn-sm" })-->
<#--            @Html.ActionLink("Details", "Details", new { id = item.ID }, new { @class = "btn btn-default btn-sm" })-->
<#--            @*@Html.ActionLink("Delete", "Delete", new { id = item.ID }, new { @class = "btn btn-danger" })*@-->
<#--        </td>-->
<#--    </tr>-->
<#--    }-->
    <#list attendees as attendee>
        <tr>
            <td>
<#--                @Html.DisplayFor(modelItem => item.Name)-->
                ${attendee.name()}
            </td>
            <td>
<#--                @Html.DisplayFor(modelItem => item.BadgeID)-->
                ${attendee.badgeId()}
            </td>
            <td>
<#--                @Html.ActionLink("Edit", "Edit", new { id = item.ID }, new { @class = "btn btn-default btn-sm" })-->
<#--                @Html.ActionLink("Details", "Details", new { id = item.ID }, new { @class = "btn btn-default btn-sm" })-->
<#--                @*@Html.ActionLink("Delete", "Delete", new { id = item.ID }, new { @class = "btn btn-danger" })*@-->
                <a href="/attendees/old/edit" class="btn btn-default btn-sm">Edit</a>
                <a href="/attendees/old/details" class="btn btn-default btn-sm">Details</a>
                <a href="/attendees/old/delete" class="btn btn-danger">Delete</a>
            </td>
        </tr>
    </#list>
</table>
<br />
<#--Page @(Model.Attendees.PageCount < Model.Attendees.PageNumber ? 0 : Model.Attendees.PageNumber) of @Model.Attendees.PageCount-->
Page 1 of 10

<#--@Html.PagedListPager(Model.Attendees, page => Url.Action("Index", new { page, currentFilter = ViewBag.CurrentFilter }))-->

<#--@section Scripts{-->
<#--<script src="~/Scripts/bootstrap-filestyle.min.js"></script>-->
<#--}-->
</@p.page>