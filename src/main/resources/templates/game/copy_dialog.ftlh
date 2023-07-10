<#import "../common/dialog_header.ftlh" as h/>
<#import "../common/duration_format.ftlh" as d/>
<#import "copy_dialog_buttons.ftlh" as b/>
<@h.header title="${copy.gameTitle()}" buttons=[b.copyEditButton, b.copyDeleteButton]/>
<div class="copyDialogContent">
    <div class="copyInfo">
        <span>${copy.library()} #${copy.libraryId()}</span>
        <span>Owner: ${copy.owner()}</span>
        <p>${copy.notes()}</p>
    </div>
    <#if copy.checkouts()?size lte 0>
        <span>No checkouts</span>
    <#else>
    <div class="checkoutTimelineContainer">
        <div class="checkoutTimelineStatus">
            <#if copy.checkouts()?first.end()??>
                <span>Checked In</span>
                <span><@d.format copy.checkouts()?first.durationUntilNextCheckout()/> ago</span>
            <#else>
                <span>Checked Out</span>
                <span><@d.format copy.checkouts()?first.duration()/> ago</span>
            </#if>
        </div>
        <div class="checkoutTimeline">
            <#list copy.checkouts() as checkout>
                <#if checkout?is_first != true>
                    <div class="checkoutTimelineGap" style="grid-row: ${checkout?index * 2};"><@d.format checkout.durationUntilNextCheckout()/></div>
                </#if>
                <div class="checkoutTimelineItemTime" style="grid-row: ${(checkout?index * 2) + 1};">${checkout.startDisplay()} - <#if checkout.end()??>${checkout.endDisplay()}</#if></div>
                <div class="checkoutTimelineItemDot" style="grid-row: ${(checkout?index * 2) + 1};"></div>
                <div class="checkoutTimelineItemText" style="grid-row: ${(checkout?index * 2) + 1};">${checkout.borrower().name()} (<@d.format checkout.duration()/>)</div>
            </#list>
        </div>
    </div>
    </#if>
</div>