import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Cliente } from '../models/cliente.model';
import { ClienteService } from '../cliente/cliente.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  clientes: Cliente[];
  regModel: Cliente;
  showNew: Boolean = false;
  term: string;

  constructor(private router: Router,
    private clienteService: ClienteService) {
  }

  ngOnInit() {
    this.clienteService.getClientes()
      .subscribe(data => {
        this.clientes = data;
      });
  }

  // This method associate to New Button.
  onNew() {
    // Initiate new cliente.
    this.regModel = new Cliente();
    // display cliente entry section.
    this.showNew = true;
  }

  // This method associate to Save Button.
  onSave() {
    // Push cliente model object into cliente list.
    this.clienteService.avaliaProposta(this.regModel).subscribe(data => {
      this.clientes.unshift(
        data
      );
    });
    // Hide cliente entry section.
    this.showNew = false;
  }

  // This method associate to Search Button.
  onSearch() {
    // Refresh objects into cliente list.
    this.clienteService.findByCpfContaining(this.term)
      .subscribe(data => {
        this.clientes = data;
      });
  }

  // This method associate toCancel Button.
  onCancel() {
    // Hide cliente entry section.
    this.showNew = false;
  }

  onKey(event: any) { // without type info
    this.term = (event.target.value);
    this.onSearch();
  }
}
