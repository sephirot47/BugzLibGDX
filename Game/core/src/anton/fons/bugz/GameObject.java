package anton.fons.bugz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;

public class GameObject
{
    protected AssetManager assets;
    private boolean assetsProcessed;
    private String modelFilepath;

    private ModelBatch modelBatch;
    protected ModelInstance modelInstance;

    protected AnimationController animationController;

    public ArrayList<GameObject> childs;

    public GameObject(String modelFilepath)
    {
        assets = new AssetManager();

        modelBatch = new ModelBatch();
        this.modelFilepath = modelFilepath;

        childs = new ArrayList<GameObject>();

        loadAssets();
    }

    protected void loadAssets()
    {
        assets.load(modelFilepath, Model.class); //load everything we need

        assetsProcessed = false;
    }

    protected boolean assetsLoaded() { return assetsProcessed; }

    protected void onAssetsLoaded()
    {
        Model model = assets.get(modelFilepath, Model.class);
        modelInstance = new ModelInstance(model); //get the model instance

        animationController = new AnimationController(modelInstance); //set the animController

        assetsProcessed = true;
    }

    public void render(Environment environment, Camera gameCamera)
    {
        if( !assets.update() ) return; //If it's still loading...
        else if (!assetsProcessed) onAssetsLoaded(); //Assets have just loaded, must be processed

        //Draw the model instance
        modelBatch.begin(gameCamera);
        modelBatch.render(modelInstance, environment);
        modelBatch.end();
        //

        //Update animations
        animationController.update(Gdx.graphics.getDeltaTime());

        for(GameObject child : childs) //Render its childs
        {
            child.render(environment, gameCamera);
        }
    }
}