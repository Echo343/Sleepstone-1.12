package com.blargsworkshop.sleepstone.items.airmattress;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class AirMattressModel extends ModelBase {
    public ModelRenderer headPiece;
    public ModelRenderer footPiece;

    public AirMattressModel()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.headPiece = new ModelRenderer(this, 0, 0);
        this.headPiece.addBox(0.0F, 0.0F, 0.0F, 16, 16, 9, 0.0F);
        this.footPiece = new ModelRenderer(this, 0, 25);
        this.footPiece.addBox(0.0F, 0.0F, 0.0F, 16, 16, 9, 0.0F);
    }

    public int getModelVersion()
    {
        return 51;
    }

    public void render()
    {
        this.headPiece.render(0.0625F);
        this.footPiece.render(0.0625F);
    }

    public void preparePiece(boolean p_193769_1_)
    {
        this.headPiece.showModel = p_193769_1_;
        this.footPiece.showModel = !p_193769_1_;
    }
}