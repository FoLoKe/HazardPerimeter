package com.foloke.haz.controllers.tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.foloke.haz.entities.Pawn;

public class WaitTask extends LeafTask<Pawn> {
    @Override
    public Status execute() {
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Pawn> copyTo(Task<Pawn> task) {
        WaitTask copyTask = (WaitTask) task;

        return task;
    }
}
