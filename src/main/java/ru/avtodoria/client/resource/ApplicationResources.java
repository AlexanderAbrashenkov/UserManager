package ru.avtodoria.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface ApplicationResources extends ClientBundle {
    public static final ApplicationResources INSTANCE = GWT.create(ApplicationResources.class);
    
    @Source("delete.png")
    @ImageOptions(repeatStyle= RepeatStyle.Both)
    ImageResource deleteIcon();
    
    @Source("add.png")
    @ImageOptions(repeatStyle= RepeatStyle.Both)
    ImageResource addIcon();
    
    @Source("edit.png")
    @ImageOptions(repeatStyle= RepeatStyle.Both)
    ImageResource editIcon();
    
    @Source("load.png")
    @ImageOptions(repeatStyle= RepeatStyle.Both)
    ImageResource loadIcon();
}
