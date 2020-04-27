package com.foloke.haz.controllers.tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.foloke.haz.entities.Pawn;

public class WalkTask extends LeafTask<Pawn> {

    @Override
    public Task.Status execute() {
        System.out.println("walk");
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Pawn> copyTo(Task<Pawn> task) {
        WalkTask copyTask = (WalkTask) task;

        return task;
    }
}
