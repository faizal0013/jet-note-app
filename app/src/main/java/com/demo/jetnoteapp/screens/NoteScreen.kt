package com.demo.jetnoteapp.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.jetnoteapp.R
import com.demo.jetnoteapp.components.NoteButton
import com.demo.jetnoteapp.components.NoteInputText
import com.demo.jetnoteapp.data.NoteDataSource
import com.demo.jetnoteapp.model.Note
import com.demo.jetnoteapp.util.formatDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
){
    Column(
        modifier = Modifier
            .padding(6.dp)
    ) {

        var title by remember {
            mutableStateOf("")
        }

        var description by remember {
            mutableStateOf("")
        }

        val context = LocalContext.current

        TopAppBar(
            title = {
                    Text(
                        text = stringResource(
                            id = R.string.app_name
                        )
                    )
            },
            actions = {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Notifications"
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color(0xFFDADFE3)
            )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            NoteInputText(
                modifier = Modifier
                    .padding(
                        top = 9.dp,
                        bottom = 8.dp
                    ),
                text = title,
                label = "Title",
                onTextChanged = {
                    val isTure = it.all {
                            char -> char.isLetter() || char.isWhitespace()
                    }

                    if(isTure){
                        title = it
                    }
                }
            )

            NoteInputText(
                modifier = Modifier
                    .padding(
                        top = 9.dp,
                        bottom = 8.dp
                    ),
                text = description,
                label = "Add a note",
                onTextChanged = {
                    val isTure = it.all {
                            char -> char.isLetter() || char.isWhitespace()
                    }

                    if(isTure){
                        description = it
                    }
                }
            )

            NoteButton(
                text = "Save",
                onClick = {
                    if(title.isNotEmpty() && description.isNotEmpty()){

                        val note = Note(
                            title = title,
                            description = description
                        )

                        onAddNote(note)

                        Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()

                        title = ""
                        description = ""
                    }
                },
                enabled = title.isNotEmpty() && description.isNotEmpty()
            )
        }

        Divider(modifier = Modifier.padding(10.dp))

        if(notes.isEmpty()){
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    . fillMaxWidth()
            ) {
                Text(
                    text = "No notes",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        LazyColumn{
            items(notes){ note ->
                NoteRow(
                    note = note,
                    onNoteClick = {
                        onRemoveNote(it)
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClick: (Note) -> Unit
){
    Surface(
        modifier = modifier
            .padding(4.dp)
            .clip(
                shape = RoundedCornerShape(
                    topEnd = 33.dp,
                    bottomStart = 33.dp,
                )
            )
            .fillMaxWidth(),
        color = Color(0XFFDFE6EB),
        shadowElevation = 6.dp,
    ) {
        Column(
            modifier = modifier
                .clickable {
                    onNoteClick(note)
                }
                .padding(horizontal = 18.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = note.description,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = formatDate(note.entryDate.time),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NoteScreenPreview(){
    NoteScreen(
        notes = NoteDataSource().loadNote(),
        onAddNote = {},
        onRemoveNote = {},
    )
}
