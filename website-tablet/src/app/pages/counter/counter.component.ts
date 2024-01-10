import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatChip } from '@angular/material/chips';
import { MatChipsModule } from '@angular/material/chips/chips-module';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { delay } from 'rxjs';
import { Main } from 'src/app/model/main';
import { Nodemcu } from 'src/app/model/nodemcu';
import { Operation } from 'src/app/model/operation/operation';
import { GoogleApiService } from 'src/app/service/google-api.service';
import { OperationService } from 'src/app/service/operation.service';
import { SheetsService } from 'src/app/service/sheets.service';
import { WebsocketService } from 'src/app/service/websocket.service';
import { DialogContagemComponent } from 'src/app/shared/dialog-contagem/dialog-contagem.component';
import { DialogHelpComponent } from 'src/app/shared/dialog-help/dialog-help.component';

@Component({
  selector: 'app-counter',
  templateUrl: './counter.component.html',
  styleUrls: ['./counter.component.scss'],
})
export class CounterComponent implements OnInit, OnDestroy {
  constructor(
    private operationService: OperationService,
    private route: ActivatedRoute,
    private _snackBar: MatSnackBar,
    public dialog: MatDialog,
    private sheetsService: SheetsService,
    private router: Router,
    private webSocketService: WebsocketService // private location: Location
  ) {}

  contagemErro: number = 1;
  contagem: number = 1;
  url: string = '';
  currentState: string = '';
  azulStateCalled: boolean = false;
  vermelhoStateCalled: boolean = false;
  verificarSeFoiUmaVez: boolean = true;
  localData: Date | undefined;
  intervalo: NodeJS.Timer | undefined;
  tempoOcioso: number = 0;
  stateButton: boolean = true;
  contadorRodando: boolean = false;
  contador: number = 0;
  intervalRef: any;
  count: number = 0;
  maintenance: number = 0;
  lmitedTime: number = 0;
  teste: any = true;
  labelPosition: string = '';
  newConter: number = 0;
  nomeOperacao: string = '';
  newMaintenance: number = 0;
  aumentarContagem: number | string = 'Alterar Contagem';
  operation: Operation = {
    id: 0,
    name: '',
    limitedTime: 0,
  };
  storage: Storage = localStorage;

  ngOnInit() {
    this.route.params.subscribe(
      (params) => {
        this.nomeOperacao = params['name'];
        this.url = this.nomeOperacao[0];
        // this.webSocketService.connect("ws://172.16.34.147:9002")
        setTimeout(() => {
          // this.webSocketService.sendMessage(this.nomeOperacao)
          if (
            new Date().getHours() >= 7 &&
            new Date().getMinutes() >= 0 &&
            new Date().getMinutes() <= 10
          ) {
            if (this.count > 30) {
              this.router.navigate(['/counter/' + this.nomeOperacao]);
            }
          }
        }, 1000);
        this.operationService.get(params['name'], this.url).subscribe(
          (res) => {
            if (res == null) {
              this.router.navigate([
                `http://172.16.34.147:4200/counter/${this.nomeOperacao}`,
              ]);
            }
            this.operation = res;
            this.operationService
              .getByName(this.operation.name, this.url)
              .subscribe(
                (res) => {
                  this.count = res.count;
                  this.maintenance = res.maintenance;
                  var newOperation = this.storage.getItem('operation')!;
                  this.newConter = parseInt(this.storage.getItem('counter')!);
                  this.newMaintenance = parseInt(
                    this.storage.getItem('maintenance')!
                  );
                  if (isNaN(this.newConter)) {
                    this.storage.setItem('operation', this.operation.name);
                    this.storage.setItem('counter', '0');
                    this.storage.setItem('maintenance', '0');
                  } else {
                    if (newOperation != this.operation.name) {
                      this.storage.setItem('operation', this.operation.name);
                      this.storage.setItem('counter', '0');
                      this.storage.setItem('maintenance', '0');
                    }
                  }
                  this.newConter = parseInt(this.storage.getItem('counter')!);
                  this.newMaintenance = parseInt(
                    this.storage.getItem('maintenance')!
                  );
                },
                (errr) => {
                  this.openSnackBar('Erro no Service', 'Ok');
                }
              );
          },
          (errr) => {
            this.router.navigate([
              `http://172.16.34.147:4200/counter/${this.nomeOperacao}`,
            ]);
          }
        );
      },
      (errr) => {
        this.router.navigate([
          `http://172.16.34.147:4200/counter/${this.nomeOperacao}`,
        ]);
      }
    );
    console.log();
    this.operationService.getTCimposto(this.url).subscribe(
      (res: Main[]) => {
        res.forEach((res) => {
          this.lmitedTime = res.tcimposto;
        });
      },
      (error) => {
        this.openSnackBar('Erro no Service', 'Ok');
      }
    );
    setInterval(() => {
      this.operationService.getTCimposto(this.url).subscribe(
        (res: Main[]) => {
          res.forEach((res) => {
            this.lmitedTime = res.tcimposto;
          });
        },
        (error) => {
          this.openSnackBar('Erro no Service', 'Ok');
        }
      );
      const data = new Date();
      if (data.getMinutes() == 0 && this.verificarSeFoiUmaVez == true) {
        this.verificarSeFoiUmaVez = false;
        var body: Nodemcu = {
          count: this.newConter,
          time: 0,
          state: 'verde',
          currentTC: this.contador,
          nameId: this.operation,
          maintenance: this.newMaintenance,
          shortestTC: this.contador,
          modelo: this.labelPosition,
        };
        this.sheetsService.submitForm(body, true).subscribe();
        this.newConter = 0;
        this.newMaintenance = 0;
        this.storage.setItem('counter', this.newConter.toString());
        this.storage.setItem('maintenance', this.newMaintenance.toString());
      } else if (data.getMinutes() == 1) {
        this.verificarSeFoiUmaVez = true;
      }
    }, 40000);
    setTimeout(() => {
      this.intervaloCounter();
    }, 1000);
  }

  enterFullscreen() {
    const element = document.documentElement;

    if (element.requestFullscreen) {
      element.requestFullscreen();
    }
  }

  intervaloCounter() {
    this.intervalo = setInterval(() => {
      this.tempoOcioso++;
      if (
        this.tempoOcioso > this.lmitedTime / 2 &&
        this.tempoOcioso < this.lmitedTime / 2 + 10
      ) {
        if (!this.azulStateCalled) {
          this.operationService.atualizarState(
            this.operation.name,
            'azul',
            this.url
          );
          this.azulStateCalled = true;
          this.vermelhoStateCalled = false; // Redefine a variável de vermelho
        }
      } else if (this.tempoOcioso > this.lmitedTime) {
        if (!this.vermelhoStateCalled) {
          this.operationService.atualizarState(
            this.operation.name,
            'vermelho',
            this.url
          );
          this.vermelhoStateCalled = true;
          this.azulStateCalled = false; // Redefine a variável de azul
        }
      }
    }, 1000);
  }

  ngOnDestroy() {
    this.stopTimer('');
  }

  toggleContagem(state: string) {
    clearInterval(this.intervalo);
    this.tempoOcioso = 0;
    if (this.contadorRodando) {
      this.tempoOcioso = 0;
      this.intervaloCounter();
      this.stopTimer(state);
      this.stateButton = true;
      this.contador = 0;
      this.operationService.atualizar(
        this.operation.name,
        this.contador,
        this.url
      );
    } else {
      if (state != 'refuse') {
        this.iniciarContagem(state);
        this.stateButton = false;
      }
    }
  }

  iniciarContagem(state: string) {
    this.currentState = 'verde';
    this.contadorRodando = true;
    this.intervalRef = setInterval(() => {
      this.contador++;
      this.operationService.atualizar(
        this.operation.name,
        this.contador,
        this.url
      );
      if (this.contador > 9999) {
        this.stopTimer(state);
      } else if (
        this.contador > this.lmitedTime &&
        this.currentState == 'azul'
      ) {
        this.currentState = 'vermelho';
        this.operationService.atualizarState(
          this.operation.name,
          'azul',
          this.url
        );
      } else if (
        this.contador > this.lmitedTime * 3 &&
        this.currentState == 'vermelho'
      ) {
        this.currentState = 'verde';
        this.operationService.atualizarState(
          this.operation.name,
          'vermelho',
          this.url
        );
      } else if (
        this.contador < this.lmitedTime &&
        this.currentState == 'verde'
      ) {
        this.currentState = 'azul';
        this.operationService.atualizarState(
          this.operation.name,
          'verde',
          this.url
        );
      }
    }, 1000);
  }

  stopTimer(state: string) {
    this.operationService.getTCimposto(this.url).subscribe((res: Main[]) => {
      this.operationService.atualizar(
        this.operation.name,
        this.contador,
        this.url
      );
      res.forEach((res) => {
        this.lmitedTime = res.tcimposto;
      });
    });
    if (state == 'count') {
      this.count += this.contagem;
      this.newConter += this.contagem;
      this.storage.setItem('counter', this.newConter.toString());
    } else if (state == 'refuse') {
      this.contadorRodando = false;
      const dialogRef = this.dialog.open(DialogContagemComponent);

      dialogRef.afterClosed().subscribe((result) => {
        if (
          result != undefined ||
          this.operation.name == 'SB20' ||
          this.operation.name == 'SB40'
        ) {
          this.contagemErro = result;
        }
        this.maintenance += this.contagemErro;
        this.newMaintenance += this.contagemErro;
        this.storage.setItem('maintenance', this.newMaintenance.toString());
        this.contadorRodando = false;
        clearInterval(this.intervalRef);
        if (
          this.contador > this.lmitedTime &&
          this.contador < this.lmitedTime * 3
        ) {
          var body: Nodemcu = {
            count: this.count,
            time: 0,
            state: 'azul',
            currentTC: this.contador,
            nameId: this.operation,
            maintenance: this.maintenance,
            shortestTC: this.contador,
            modelo: this.labelPosition,
          };
          this.operationService.post(body, this.url).subscribe((res) => {
            if (res) {
              this.openSnackBar('Enviado com sucesso', 'Ok');
            } else {
              this.count--;
              this.openSnackBar('Erro ao enviar', 'Ok');
            }
          });
        } else if (this.contador >= this.lmitedTime * 3) {
          var body: Nodemcu = {
            count: this.count,
            time: 0,
            state: 'vermelho',
            currentTC: this.contador,
            nameId: this.operation,
            maintenance: this.maintenance,
            shortestTC: this.contador,
            modelo: this.labelPosition,
          };
          this.operationService.post(body, this.url).subscribe((res) => {
            if (res) {
              this.openSnackBar('Enviado com sucesso', 'Ok');
            } else {
              this.count--;
              this.openSnackBar('Erro ao enviar', 'Ok');
            }
          });
        } else {
          var body: Nodemcu = {
            count: this.count,
            time: 0,
            state: 'verde',
            currentTC: this.contador,
            nameId: this.operation,
            maintenance: this.maintenance,
            shortestTC: this.contador,
            modelo: this.labelPosition,
          };
          this.operationService.post(body, this.url).subscribe((res) => {
            if (res) {
              this.openSnackBar('Enviado com sucesso', 'Ok');
            } else {
              this.count--;
              this.openSnackBar('Erro ao enviar', 'Ok');
            }
          });
        }
        this.contador = 0; // Reseta o contador para 0 quando a contagem é parada
        this.operationService.atualizar(this.operation.name, 0, this.url);
        var body: Nodemcu = {
          count: this.newConter,
          time: 0,
          state: 'verde',
          currentTC: this.contador,
          nameId: this.operation,
          maintenance: this.newMaintenance,
          shortestTC: this.contador,
          modelo: this.labelPosition,
        };
        this.sheetsService.submitForm(body, false).subscribe();
      });
    }
    if (state == 'count') {
      this.contadorRodando = false;
      clearInterval(this.intervalRef);
      if (
        this.contador > this.lmitedTime &&
        this.contador < this.lmitedTime * 3
      ) {
        var body: Nodemcu = {
          count: this.count,
          time: 0,
          state: 'azul',
          currentTC: this.contador,
          nameId: this.operation,
          maintenance: this.maintenance,
          shortestTC: this.contador,
          modelo: this.labelPosition,
        };
        this.operationService.post(body, this.url).subscribe((res) => {
          if (res) {
            this.openSnackBar('Enviado com sucesso', 'Ok');
          } else {
            this.count--;
            this.openSnackBar('Erro ao enviar', 'Ok');
          }
        });
      } else if (this.contador >= this.lmitedTime * 3) {
        var body: Nodemcu = {
          count: this.count,
          time: 0,
          state: 'vermelho',
          currentTC: this.contador,
          nameId: this.operation,
          maintenance: this.maintenance,
          shortestTC: this.contador,
          modelo: this.labelPosition,
        };
        this.operationService.post(body, this.url).subscribe((res) => {
          if (res) {
            this.openSnackBar('Enviado com sucesso', 'Ok');
          } else {
            this.count--;
            this.openSnackBar('Erro ao enviar', 'Ok');
          }
        });
      } else {
        var body: Nodemcu = {
          count: this.count,
          time: 0,
          state: 'verde',
          currentTC: this.contador,
          nameId: this.operation,
          maintenance: this.maintenance,
          shortestTC: this.contador,
          modelo: this.labelPosition,
        };
        this.operationService.post(body, this.url).subscribe((res) => {
          if (res) {
            this.openSnackBar('Enviado com sucesso', 'Ok');
          } else {
            this.count--;
            this.openSnackBar('Erro ao enviar', 'Ok');
          }
        });
      }
      this.contador = 0; // Reseta o contador para 0 quando a contagem é parada
      this.operationService.atualizar(this.operation.name, 0, this.url);
      var body: Nodemcu = {
        count: this.newConter,
        time: 0,
        state: 'verde',
        currentTC: this.contador,
        nameId: this.operation,
        maintenance: this.newMaintenance,
        shortestTC: this.contador,
        modelo: this.labelPosition,
      };
      this.sheetsService.submitForm(body, false).subscribe();
    }
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 3000,
    });
  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogHelpComponent, {
      data: [''],
    });
  }

  openDialogContagem() {
    const dialogRef = this.dialog.open(DialogContagemComponent);

    dialogRef.afterClosed().subscribe((result) => {
      if (result != undefined) {
        this.aumentarContagem = '+' + result;
        this.contagem = result;
      }
    });
  }
}
