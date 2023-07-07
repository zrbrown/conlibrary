<#--@using PagedList.Mvc;-->
<#--@model PagedList.IPagedList<BoardGameLibrary.Data.Models.Checkout>-->

<#--    @{-->
<#--    ViewBag.Title = "Index";-->
<#--    Layout = "~/Views/Shared/_Layout.cshtml";-->
<#--    }-->
<#import "../common/page.ftlh" as p/>
<@p.page>
<h2>Checkout History</h2>

<#--@using (Html.BeginForm("Index", "Checkouts", FormMethod.Get, new { @class = "form-inline" }))-->
<#--{-->
<p>
<#--    @Html.TextBox("SearchString", null, new { @class = "form-control text-box", placeholder = "Title, library ID, or attendee name/ID" })-->
    <input type="text" placeholder="Title, library ID, or attendee name/ID" class="form-control text-box"/>
    <input type="submit" value="Search" class="btn btn-primary" />
</p>
<#--}-->
<table class="checkout-history-table table">
    <tr>
        <th>Copy</th>
        <th>Attendee</th>
        <th>Length</th>
        <th>
            <span class="glyphicon glyphicon-sort"></span>
<#--            @{-->
<#--            object timeOutLinkOptions = null;-->
<#--            if (((string)ViewBag.SortOrder).Contains("time_out"))-->
<#--            {-->
<#--            timeOutLinkOptions = new { @class = "current-sort" };-->
<#--            }-->
<#--            }-->
<#--            @Html.ActionLink("Time Out", "Index", new { sortOrder = ViewBag.TimeOutSort }, timeOutLinkOptions)-->
            <a href="" class="current-sort">Time Out</a>
        </th>
        <th>Checked in at</th>
        <th>
            <span class="glyphicon glyphicon-sort"></span>
<#--            @{-->
<#--            object playLinkOptions = null;-->
<#--            if (((string)ViewBag.SortOrder).Contains("play"))-->
<#--            {-->
<#--            playLinkOptions = new { @class = "current-sort" };-->
<#--            }-->
<#--            }-->
<#--            @Html.ActionLink("Play Entered", "Index", new { sortOrder = ViewBag.PlaySort }, playLinkOptions)-->
            <a href="" class="current-sort">Play Entered</a>
        </th>
    </tr>

    <#list checkouts as checkout>
        <tr>
            <td>${checkout.copy().game().title()} (${checkout.copy().library().name()})</td>
            <td>${checkout.attendee().name()} (${checkout.attendee().badgeId()})</td>
            <td>${checkout.duration()}</td>
            <td>${checkout.start()}</td>
            <td>${checkout.end()}</td>
            <td><#if checkout.playRegistered()>Yes<#else>No</#if></td>
        </tr>
    </#list>

</table>
<br />
<#--Page @(Model.PageCount < Model.PageNumber ? 0 : Model.PageNumber) of @Model.PageCount-->
Page 1 of 9

<#--@Html.PagedListPager(Model, page => Url.Action("Index", new { page, currentFilter = ViewBag.CurrentFilter, sortOrder = ViewBag.SortOrder }))-->
</@p.page>