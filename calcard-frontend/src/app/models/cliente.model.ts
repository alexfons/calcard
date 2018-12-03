export class Cliente {
  constructor(
    public id: string = '',
    public nome: string = '',
    public cpf: string = '',
    public idade: string = '',
    public sexo: string = '',
    public estadoCivil: string = '',
    public estado: string = '',
    public dependentes: string = '',
    public renda: string = '',
    public resultado: string = '',
    public limite: string = ''
  ) { }
}