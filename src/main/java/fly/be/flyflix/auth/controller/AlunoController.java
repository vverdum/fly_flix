package fly.be.flyflix.auth.controller;

import fly.be.flyflix.auth.controller.dto.CadastroAlunoDTO;
import fly.be.flyflix.auth.repository.AlunoRepository;
import fly.be.flyflix.auth.service.EmailService;
import fly.be.flyflix.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroAlunoDTO dados) {

        return userService.cadastrarUsuario(dados);
//        ResponseEntity<Void> cadastrarUsuario(CadastroAlunoDTO dados)
//        var aluno = new Aluno(dados);
//        var usuario = new Usuario(aluno);
//        repository.save(aluno);
//
//        emailService.sendEmail(aluno.getEmail(), "Cadastro realizado com sucesso", "Sua conta foi criada com sucesso");
//
//        return ResponseEntity.ok();
    }

//    @GetMapping
//    public Page<DadosDetalhamentoAluno> listar(@PageableDefault(size=10, sort = {"nome"}) Pageable paginacao) {
//        return repository.findAllByAtivoTrue(paginacao).map(DadosDetalhamentoAluno::new);
//    }
//
//    @PutMapping
//    @Transactional
//    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoAluno dados) {
//        var aluno = repository.getReferenceById(dados.id());
//        aluno.atualizarInformacoes(dados);
//
//        return ResponseEntity.ok(new DadosDetalhamentoAluno(aluno));
//    }
//
//    @DeleteMapping("/{id}")
//    @Transactional
//    public ResponseEntity remover(@PathVariable Long id) {
//        var aluno = repository.getReferenceById(id);
//        aluno.inativar();
//        return ResponseEntity.noContent().build();
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity detalhar(@PathVariable Long id) {
//        var aluno = repository.getReferenceById(id);
//        return ResponseEntity.ok(new DadosDetalhamentoAluno(aluno));
//    }
}
