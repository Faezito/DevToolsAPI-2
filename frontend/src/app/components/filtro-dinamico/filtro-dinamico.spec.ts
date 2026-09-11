import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltroDinamico } from './filtro-dinamico';

describe('FiltroDinamico', () => {
  let component: FiltroDinamico;
  let fixture: ComponentFixture<FiltroDinamico>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FiltroDinamico],
    }).compileComponents();

    fixture = TestBed.createComponent(FiltroDinamico);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
