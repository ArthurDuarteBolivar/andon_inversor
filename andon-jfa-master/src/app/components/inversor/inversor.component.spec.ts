import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InversorComponent } from './inversor.component';

describe('InversorComponent', () => {
  let component: InversorComponent;
  let fixture: ComponentFixture<InversorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InversorComponent]
    });
    fixture = TestBed.createComponent(InversorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
