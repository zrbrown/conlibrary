<#import "../common/dialog_header.ftlh" as h/>
<#import "../common/duration_format.ftlh" as d/>
<#import "copy_dialog_buttons.ftlh" as b/>
<@h.header title="${copy.gameTitle()}" buttons=[b.copyEditButton, b.copyDeleteButton]/>
<div class="copyDialogContent">
    <div class="copyInfo">
        <span>${copy.libraryName()} #${copy.libraryCopyId()}</span>
        <span>Owner: ${copy.owner()}</span>
        <p>${copy.notes()}</p>
    </div>
    <#if checkouts?size lte 0>
        <span>No checkouts</span>
    <#else>
    <div class="checkoutTimelineContainer">
        <div class="checkoutTimelineStatus">
            <#if checkouts?first.end()??>
                <span>Checked In</span>
                <span><@d.format checkouts?first.durationUntilNextCheckout()/> ago</span>
            <#else>
                <span>Checked Out</span>
                <span><@d.format checkouts?first.duration()/> ago</span>
            </#if>
        </div>
        <div class="checkoutTimeline">
            <#list checkouts as checkout>
                <#if checkout?is_first != true>
                    <div class="checkoutTimelineGap" style="grid-row: ${checkout?index * 2};"><@d.format checkout.durationUntilNextCheckout()/></div>
                </#if>
                <div class="checkoutTimelineItemTime" style="grid-row: ${(checkout?index * 2) + 1};">
                    ${checkout.startDisplay()} - <#if checkout.end()??>${checkout.endDisplay()}</#if>
                </div>
                <div class="checkoutTimelineItemDot" style="grid-row: ${(checkout?index * 2) + 1};"></div>
                <div class="checkoutTimelineItemText" style="grid-row: ${(checkout?index * 2) + 1};">
                    ${checkout.borrower().name()} (<@d.format checkout.duration()/>)
                </div>
            </#list>
        </div>
    </div>
    </#if>
</div>