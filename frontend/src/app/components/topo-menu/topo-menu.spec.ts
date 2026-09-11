import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopoMenu } from './topo-menu';

describe('TopoMenu', () => {
  let component: TopoMenu;
  let fixture: ComponentFixture<TopoMenu>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopoMenu],
    }).compileComponents();

    fixture = TestBed.createComponent(TopoMenu);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
