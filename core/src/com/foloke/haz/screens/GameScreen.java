package com.foloke.haz.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.foloke.haz.HPGame;
import com.foloke.haz.Level;
import com.foloke.haz.components.Inventory;
import com.foloke.haz.controllers.AI;
import com.foloke.haz.controllers.Controller;
import com.foloke.haz.entities.Character;
import com.foloke.haz.entities.Drop;
import com.foloke.haz.entities.Pawn;
import com.foloke.haz.ui.BTreeUI;
import com.foloke.haz.ui.InventoryUI;
import com.foloke.haz.ui.PawnDebugUI;
import com.foloke.haz.ui.UIStage;
import com.foloke.haz.utils.HPContactFilter;
import com.foloke.haz.utils.HPContactListener;
import com.foloke.haz.utils.SkeletonUtils;

import static com.foloke.haz.HPGame.skin;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    public static Texture img;
    public static TextureRegion texture;
    public static Sound sound;
    public static Sound rsound;


    private OrthographicCamera camera;
    public World world;
    private Box2DDebugRenderer debugRenderer;
    private Controller controller;
    public ShapeRenderer shapeDebugRenderer;
    public static final int PPM = 32;

    Level level;

    HPGame hpGame;
    UIStage stage;
    InventoryUI inventoryUI;
    static PawnDebugUI pawnDebugUI;

    public static SkeletonRenderer skeletonRenderer;

    public GameScreen(HPGame hpGame) {
        this.hpGame = hpGame;
    }

    @Override
    public void show() {
        SkeletonUtils.init();

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.wav"));
        rsound = Gdx.audio.newSound(Gdx.files.internal("sounds/reloading.wav"));

        world = new World(new Vector2(0, -25), true);
        world.setContactListener(new HPContactListener());
        world.setContactFilter(new HPContactFilter());

        debugRenderer = new Box2DDebugRenderer();
        shapeDebugRenderer = new ShapeRenderer();
        shapeDebugRenderer.setAutoShapeType(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.25f;
        texture = new TextureRegion(img, 32, 32);

        Pawn player = new Character(world);
        controller = new Controller(player);
        player.setController(controller);

        level = new Level(this, "levels/debugMap.tmx");

        level.spawn(player);

        Pawn pawn = new Character(world);
        pawn.setController(new AI(pawn));
        level.spawn(pawn);

        stage = new UIStage();
        Table table = new Table();
        table.setSize(Gdx.graphics.getWidth() / 3f, 64);
        table.setPosition(10, Gdx.graphics.getHeight() - table.getHeight() - 10);

        stage.addActor(table);
//        energyBar = new ColoredBar(Color.BLUE, Color.valueOf("007cad"), 8, 1);
//        hpBar = new ColoredBar(Color.RED, Color.GREEN, 16, 1);
        Label pointsLabel = new Label("Points", skin);
        pointsLabel.setDebug(true);

        pointsLabel.setWidth(16);
        pointsLabel.setWrap(true);
        table.addActor(pointsLabel);
//
//        table.add(hpBar).width(Gdx.graphics.getWidth() / 3f);
//        table.row();
//        table.add(energyBar).width(Gdx.graphics.getWidth() / 3f);

        inventoryUI = new InventoryUI(stage, this);
        inventoryUI.update(controller.getInventory());
        inventoryUI.setSize(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() - 54);
        inventoryUI.setPosition(10, Gdx.graphics.getHeight() - inventoryUI.getHeight() - 44);
        inventoryUI.setVisible(false);
        stage.addActor(inventoryUI);

        BTreeUI<Pawn> bTreeUI = new BTreeUI<Pawn>(stage);
        bTreeUI.debug(((AI)pawn.getController()).getBehaviorTree());
        bTreeUI.setSize(320, 320);
        stage.addActor(bTreeUI);

        pawnDebugUI = new PawnDebugUI(stage);
        pawnDebugUI.debug(pawn);
        pawnDebugUI.setSize(320, 320);
        stage.addActor(pawnDebugUI);

        Gdx.input.setInputProcessor(stage);

        skeletonRenderer = new SkeletonRenderer();
        skeletonRenderer.setPremultipliedAlpha(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        manageInput();

        camera.position.set(controller.getPosition().x * PPM, controller.getPosition().y * PPM, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        level.render(batch);

        shapeDebugRenderer.setProjectionMatrix(camera.combined);
        shapeDebugRenderer.begin();
        level.debug(shapeDebugRenderer);
        shapeDebugRenderer.end();



        world.step(1 / 60f, 6, 2);

        debugRenderer.render(world, camera.combined.scl(PPM));
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public void manageInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                controller.move(Controller.MovementType.RUN, false);
            } else if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                controller.move(Controller.MovementType.WALK, false);
            } else {
                controller.move(Controller.MovementType.NORMAL, false);
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                controller.move(Controller.MovementType.RUN, true);
            } else if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                controller.move(Controller.MovementType.WALK, true);
            } else {
                controller.move(Controller.MovementType.NORMAL, true);
            }
        } else {
            controller.stop();
        }

        Vector2 worldHit = screenToWorld(Gdx.input.getX(), Gdx.input.getY());
        controller.lookTo(worldHit);

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if(stage.hit(Gdx.input.getX(), Gdx.input.getY(), false) == null && !stage.isUiTouched()) {
                Array<Fixture> fixtures = new Array<>();

                world.getFixtures(fixtures);
                boolean hit = false;
                for(Fixture fixture : fixtures) {
                    if(fixture.getUserData() instanceof Pawn) {
                        hit = fixture.testPoint(worldHit);
                        if(hit) {
                            System.out.println("hit");
                            pawnDebugUI.debug((Pawn)fixture.getUserData());
                            break;
                        }
                    }
                }

                if(!hit) {
                    controller.shoot();
                }
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            controller.pickUp();
            inventoryUI.update(controller.getInventory());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            controller.jump();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom+=0.25;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom-=0.25;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            if(!inventoryUI.isVisible()) {
                inventoryUI.update(controller.getInventory());
            }
            inventoryUI.setVisible(!inventoryUI.isVisible());
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void drop(Inventory inventory, Inventory.Item item, int count) {
        if (inventory.remove(item, count)) {
            Drop drop = new Drop(world, new Inventory.Item(item.ID, count));
            level.addEntity(drop);
            drop.setPosition(controller.getPosition());
        }
    }

    private Vector2 screenToWorld(float x, float y) {
        return new Vector2(((x - Gdx.graphics.getWidth() / 2f) * camera.zoom + camera.position.x ) / PPM,
        -((y - Gdx.graphics.getHeight() / 2f) * camera.zoom  - camera.position.y) / PPM);
    }
}
