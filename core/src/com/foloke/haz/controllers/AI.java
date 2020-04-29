package com.foloke.haz.controllers;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibrary;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeReader;
import com.foloke.haz.entities.Pawn;

import java.util.Random;

public class AI extends Controller {

    private BehaviorTree<Pawn> behaviorTree;

    public AI(Pawn entity) {
        super(entity);

        BehaviorTreeLibraryManager behaviorTreeLibraryManager = BehaviorTreeLibraryManager.getInstance();
        behaviorTreeLibraryManager.setLibrary(new BehaviorTreeLibrary(BehaviorTreeParser.DEBUG_HIGH));
        this.behaviorTree = behaviorTreeLibraryManager.createBehaviorTree("btrees/character", entity);
        behaviorTree.start();
    }

    public void act(float delta) {
        behaviorTree.step();
        GdxAI.getTimepiece().update(delta);
    }

    public BehaviorTree<Pawn> getBehaviorTree() {
        return behaviorTree;
    }
}
