import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogContagemComponent } from './dialog-contagem.component';

describe('DialogContagemComponent', () => {
  let component: DialogContagemComponent;
  let fixture: ComponentFixture<DialogContagemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogContagemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogContagemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
