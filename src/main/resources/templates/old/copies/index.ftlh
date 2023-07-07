<#--@model IEnumerable<BoardGameLibrary.Data.Models.Copy>-->

<#--    @{-->
<#--    ViewBag.Title = "Index";-->
<#--    Layout = "~/Views/Shared/_Layout.cshtml";-->
<#--    }-->
<#import "../common/page.ftlh" as p/>
<@p.page>
<h2>Copies of <strong>${game.title()}</strong></h2>
<p>
<#--        @Html.ActionLink("Add Copy", "Create", new { gameID = ViewBag.GameID }, new { @class = "btn btn-primary" })-->
    <a href="/copies/old/create" class="btn btn-primary">Add Copy</a>
</p>
<table class="table">
    <tr>
        <th>Library ID</th>
        <th>Owner</th>
        <th>Notes</th>
        <th></th>
    </tr>

<#--        @if (Model != null)-->
<#--        {-->
<#--        foreach (var item in Model)-->
<#--        {-->
<#--        <tr>-->
<#--            <td>-->
<#--                @Html.DisplayFor(modelItem => item.LibraryID)-->
<#--            </td>-->
<#--            <td>-->
<#--                @Html.DisplayFor(modelItem => item.OwnerName)-->
<#--            </td>-->
<#--            <td>-->
<#--                @Html.DisplayFor(modelItem => item.Notes)-->
<#--            </td>-->
<#--            <td>-->
<#--                @Html.ActionLink("Edit", "Edit", new { id = item.ID }, new { @class = "btn btn-default" })-->
<#--                @Html.ActionLink("Details", "Details", new { id = item.ID }, new { @class = "btn btn-default" })-->
<#--                @Html.ActionLink("Delete", "Delete", new { id = item.ID }, new { @class = "btn btn-danger" })-->
<#--            </td>-->
<#--        </tr>-->
<#--        }-->
<#--        }-->

    <#list copies as copy>
        <tr>
            <td>${copy.libraryId()}</td>
            <td>${copy.ownerName()}</td>
            <td>${copy.notes()}</td>
            <td>
                <a href="/copies/old/edit" class="btn btn-default">Edit</a>
                <a href="/copies/old/details" class="btn btn-default">Details</a>
                <a href="/copies/old/delete" class="btn btn-danger">Delete</a>
            </td>
        </tr>
    </#list>
</table>
</@p.page>