<#import "../common/dialog_header.ftlh" as h/>
<#import "../common/duration_format.ftlh" as d/>
<#import "attendee_dialog_buttons.ftlh" as b/>
<@h.header title="${attendee.name()} ${attendee.surname()} (${attendee.pronouns()})" buttons=[b.attendeeEditButton, b.attendeeDeleteButton]/>
<div class="attendeeDialogContent">
    <div class="attendeeInfo">
        <div class="attendeeBadgeId">Badge #${attendee.badgeId()}</div>
    </div>
    <div class="attendeeTimeline">
        <#list attendee.activities() as activity>
            <#if !activity?is_last || activity.copyCheckouts()?size gt 0 || activity.plays()?size gt 0>
                <div class="attendeeTimelineItem" style="grid-row: ${activity?counter};">
                    <div class="attendeeTimelineTime">
                        ${activity.startDisplay()} - <#if activity.end()??>${activity.endDisplay()}</#if>
                        <span class="attendeeTimelineTimeDuration"><@d.format activity.duration()/></span>
                    </div>
                    <div class="attendeeTimelineItemCheckouts">
                        <#list activity.copyCheckouts() as copy>
                            <span<#if !activity.plays()?keys?seq_contains(copy)> class="attendeeTimelineAttention"</#if>>${copy.gameTitle()}<#sep><br/></#sep></span>
                        <#else>-</#list>
                    </div>
                    <div class="attendeeTimelineLine"></div>
                    <div class="attendeeTimelineItemDot"></div>
                    <div class="attendeeTimelineItemPlays">
                        <#list activity.plays() as copy, duration>
                            <span<#if !activity.copyCheckouts()?seq_contains(copy)> class="attendeeTimelineAttention"</#if>>${copy.gameTitle()} (<@d.format duration/>)<#sep><br/></#sep></span>
                        <#else>-</#list>
                    </div>
                </div>
            </#if>
        </#list>
        <div class="attendeeTimelineEventCheckin" style="grid-row: ${attendee.activities()?size + 1};">Event Check in Tue 10:45 AM</div>
    </div>
</div>