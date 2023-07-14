<#import "../common/dialog_header.ftlh" as h/>
<#import "../common/duration_format.ftlh" as d/>
<#import "attendee_dialog_buttons.ftlh" as b/>
<#macro edit><@b.attendeeEditButton attendee.id()/></#macro>
<#macro delete><@b.attendeeDeleteButton attendee.id()/></#macro>
<@h.header color="#0062cc" title="${attendee.name()} ${attendee.surname()} (${attendee.pronouns()})" buttons=[edit, delete]/>
<div class="attendeeDialogContent">
    <div class="attendeeInfo">
        <div class="attendeeBadgeId">Badge #${attendee.badgeId()}</div>
    </div>
    <div class="attendeeTimeline">
        <#list activities as activity>
            <#if !activity?is_last || activity.copyCheckouts()?size gt 0 || activity.plays()?size gt 0>
                <div class="attendeeTimelineItem" style="grid-row: ${activity?counter};">
                    <div class="attendeeTimelineTime">
                        ${activity.startDisplay()} - <#if activity.end()??>${activity.endDisplay()}</#if>
                        <span class="attendeeTimelineTimeDuration"><@d.format activity.duration()/></span>
                    </div>
                    <#if activity.copyCheckouts()?size gt 0 || activity.plays()?size gt 0>
                        <div class="attendeeTimelineItemCheckouts">
                            <#list activity.copyCheckouts() as copy>
                                <span<#if activity.plays()?size gt 0 && !activity.plays()?keys?seq_contains(copy)> class="attendeeTimelineAttention"</#if>>${copy.gameTitle()}<#sep><br/></#sep></span>
                            <#else>-</#list>
                        </div>
                        <div class="attendeeTimelineLine"></div>
                        <div class="attendeeTimelineItemDot"></div>
                        <div class="attendeeTimelineItemPlays">
                            <#list activity.plays() as copy, duration>
                                <span<#if activity.copyCheckouts()?size gt 0 && !activity.copyCheckouts()?seq_contains(copy)> class="attendeeTimelineAttention"</#if>>${copy.gameTitle()} (<@d.format duration/>)<#sep><br/></#sep></span>
                            <#else>-</#list>
                        </div>
                    <#else>
                        <div class="attendeeTimelineLine"></div>
                    </#if>
                </div>
            </#if>
        </#list>
        <div class="attendeeTimelineEventCheckin" style="grid-row: ${activities?size + 1};">Event Check in Tue 10:45 AM</div>
    </div>
</div>