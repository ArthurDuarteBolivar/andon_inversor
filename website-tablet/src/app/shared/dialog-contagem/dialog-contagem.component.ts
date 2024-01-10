import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-contagem',
  templateUrl: './dialog-contagem.component.html',
  styleUrls: ['./dialog-contagem.component.scss']
})
export class DialogContagemComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<string>,
    @Inject(MAT_DIALOG_DATA) public data: any[]){ }

  ngOnInit(): void {
  }

}
