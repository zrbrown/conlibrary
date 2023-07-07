<#import "copy_dialog_buttons.ftlh" as b/>
<div hx-swap-oob="outerHtml:.dialogHeaderButtons">
    <@b.copyCancelButton/>
    <@b.copySaveButton/>
</div>
<div class="copyInfo" hx-swap-oob="outerHtml:.copyInfo">
    <label for="">Library</label>
    <input type="text" name="library" value="${copy.library()}"/>
    <label for="">Library #</label>
    <input type="text" name="libraryId" value="${copy.libraryId()}"/>
    <label for="">Owner</label>
    <input type="text" name="owner" value="${copy.owner()}"/>
    <label for="">Notes</label>
    <input type="text" name="notes" value="${copy.notes()}"/>
</div>