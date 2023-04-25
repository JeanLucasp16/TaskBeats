package com.comunidadedevspace.taskbeats


import android.app.Activity
import android.app.Instrumentation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskList = listOf<Task>(
            Task("Title0", "Desc0"),
            Task("Title1", "Desc1"),
            Task("Title2", "Desc2"),
            Task("Title3", "Desc3"),
            Task("Title4", "Desc4"),
            Task("Title5", "Desc5"),
            Task("Title6", "Desc6"),
            Task("Title7", "Desc7"),
            Task("Title8", "Desc8"),
            Task("Title9", "Desc9"),
            Task("Title10", "Desc10"),


            )
        val adapter: TaskListAdapter = TaskListAdapter(taskList, ::openTaskDetailView)
        val rvTask: RecyclerView = findViewById(R.id.rv_task_list)
        rvTask.adapter = adapter
    }

    private fun openTaskDetailView(task: Task) {
        val intent = TaskDetailActivity.start(this, task)

        val startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: Instrumentation.ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK)
        }


        startForResult.launch(intent)


    }
}