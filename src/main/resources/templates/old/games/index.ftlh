<#--@using PagedList.Mvc;-->
<#--@model GameIndexViewModel-->
<#--@using BoardGameLibrary.Models;-->
<#--@{-->
<#--ViewBag.Title = "Index";-->
<#--Layout = "~/Views/Shared/_Layout.cshtml";-->
<#--}-->
<#import "../common/page.ftlh" as p/>
<@p.page>
<h2>Games</h2>
<#--@{-->
<#--ErrorList errorList = (ErrorList)ViewBag.Errors;-->
<#--if (errorList != null && errorList.Errors.Any())-->
<#--{-->

<div class="alert alert-error">
    <a class="close" data-dismiss="alert">×</a>
    <strong>Errors</strong>
    <ul>
<#--        @foreach (var err in errorList.Errors)-->
<#--        {-->
        <li>@err</li>
<#--        }-->
    </ul>
</div>
<#--}-->
<#--}-->
<div class="row">
    <div class="col-md-1">
<#--        @Html.ActionLink("Create New", "Create", null, new { @class = "btn btn-primary" })-->
        <a href="/games/old/create" class="btn btn-primary">Create New</a>
    </div>
    <div class="col-md-offset-1 col-md-4">
<#--        @using (Html.BeginForm("Index", "Games", FormMethod.Get, new { @class = "form-inline" }))-->
<#--        {-->
<#--        <div class="row">-->
<#--            @Html.TextBox("SearchString", null, new { @class = "form-control text-box col-md-1", placeholder = "Title or library ID" })-->
<#--            <input type="submit" value="Search" class="btn btn-primary col-md-3" />-->
<#--        </div>-->
<#--        }-->
        <form class="form-inline">
            <div class="row">
                <input type="text" class="form-control text-box col-md-1" placeholder="Title or library ID"/>
                <input type="submit" value="Search" class="btn btn-primary col-md-3"/>
            </div>
        </form>
    </div>
<#--    @if(User.IsInRole("AdminUI-Admin"))-->
<#--    {-->
<#--    <div class="col-md-offset-1 col-md-5">-->
<#--        @using (Html.BeginForm("Import", "Games", FormMethod.Post, new { enctype = "multipart/form-data" }))-->
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
<table class="table table-hover table-responsive">
    <tr>
        <th>
<#--            @Html.DisplayNameFor(model => model.Games[0].Title)-->
            Title
        </th>
        <th>
            # of Copies
        </th>
        <th></th>
    </tr>

<#--    @foreach (var item in Model.Games) {-->
<#--    <tr>-->
<#--        <td>-->
<#--            @Html.DisplayFor(modelItem => item.Title)-->
<#--        </td>-->
<#--        <td>-->
<#--            @if (item.Copies != null)-->
<#--            {-->
<#--            <span>@item.Copies.Count</span>-->
<#--            }-->
<#--            else-->
<#--            {-->
<#--            <span>0</span>-->
<#--            }-->
<#--        </td>-->
<#--        <td>-->
<#--            @Html.ActionLink("Manage Copies", "Index", "Copies", new { gameID = item.ID }, new { @class = "btn btn-default" })-->
<#--            @Html.ActionLink("Edit", "Edit", new { id = item.ID }, new { @class = "btn btn-default" })-->
<#--            @Html.ActionLink("Details", "Details", new { id = item.ID }, new { @class = "btn btn-default" })-->
<#--            @if (item.Copies.Count == 0)-->
<#--            {-->
<#--            @Html.ActionLink("Delete", "Delete", new { id = item.ID }, new { @class = "btn btn-danger" })-->
<#--            }-->
<#--        </td>-->
<#--    </tr>-->
<#--    }-->
    <#list games as game>
        <tr>
            <td>${game.title()}</td>
            <td><span>${game.copies()?size}</span></td>
            <td>
                <a href="/copies/old/index" class="btn btn-default">Manage Copies</a>
                <a href="/games/old/edit" class="btn btn-default">Edit</a>
                <a href="/games/old/details" class="btn btn-default">Details</a>
                <#if game.copies()?size gt 0><a href="/games/old/delete" class="btn btn-danger">Delete</a></#if>
            </td>
        </tr>
    </#list>
</table>
<br />
<#--Page @(Model.Games.PageCount < Model.Games.PageNumber ? 0 : Model.Games.PageNumber) of @Model.Games.PageCount-->
Page 1 of 8

<#--@Html.PagedListPager(Model.Games, page => Url.Action("Index", new { page, currentFilter = ViewBag.CurrentFilter }))-->

<#--@section Scripts{-->
<#--<script src="~/Scripts/bootstrap-filestyle.min.js"></script>-->
<#--}-->
</@p.page>