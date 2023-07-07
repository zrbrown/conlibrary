<#--@model IEnumerable<BoardGameLibrary.Data.Models.Copy>-->

<#--    @{-->
<#--    ViewBag.Title = "ListCheckedOutCopies";-->
<#--    Layout = "~/Views/Shared/_Layout.cshtml";-->
<#--    }-->

<h2>Checked Out Copies</h2>
<table class="table">
    <tr>
        <th>
<#--            @Html.DisplayNameFor(model => model.LibraryID)-->
            Library ID
        </th>
        <th>
<#--            @Html.DisplayNameFor(model => model.Game.Title)-->
            Title
        </th>
        <th>
<#--            @Html.DisplayNameFor(model => model.OwnerName)-->
            Owner Name
        </th>
        <th>
<#--            @Html.DisplayNameFor(model => model.CurrentCheckout.Length)-->
            Length
        </th>
        <th>
<#--            @Html.DisplayNameFor(model => model.CurrentCheckout.Attendee)-->
            Attendee
        </th>
        <th></th>
    </tr>
<#--    @foreach (var item in Model)-->
<#--    {-->
<#--    <tr>-->
<#--        <td>-->
<#--&lt;#&ndash;            @Html.DisplayFor(modelItem => item.LibraryID)&ndash;&gt;-->
<#--        </td>-->
<#--        <td>-->
<#--&lt;#&ndash;            @Html.DisplayFor(modelItem => item.Game.Title)&ndash;&gt;-->
<#--        </td>-->
<#--        <td>-->
<#--&lt;#&ndash;            @Html.DisplayFor(modelItem => item.OwnerName)&ndash;&gt;-->
<#--        </td>-->
<#--        <td>-->
<#--            @{ var timespan = item.CurrentCheckout.Length; }-->
<#--            @string.Format("{0} days, {1} hours, {2} minutes", timespan.Days, timespan.Hours, timespan.Minutes)-->
<#--        </td>-->
<#--        <td>-->
<#--            @string.Format("{0} (# {1})", item.CurrentCheckout.Attendee.Name, item.CurrentCheckout.Attendee.BadgeID)-->
<#--        </td>-->
<#--        <td>-->

<#--        </td>-->
<#--    </tr>-->
<#--    }-->
    <#list copies as copy>
        <tr>
            <td>${copy.libraryId()?c}</td>
            <td>${copy.game().title()}</td>
            <td>${copy.owner()}</td>
            <td>${copy.currentCheckout().duration()}</td>
            <td>${copy.currentCheckout().attendee().name()} (${copy.currentCheckout().attendee().badgeId()})</td>
            <td></td>
        </tr>
    </#list>
</table>
