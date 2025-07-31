import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketControlComponent } from './ticket-control.component';

describe('TicketControlComponent', () => {
  let component: TicketControlComponent;
  let fixture: ComponentFixture<TicketControlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketControlComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
