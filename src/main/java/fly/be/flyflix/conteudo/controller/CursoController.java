package fly.be.flyflix.conteudo.controller;

import fly.be.flyflix.conteudo.dto.curso.AtualizacaoCurso;
import fly.be.flyflix.conteudo.dto.curso.CadastroCurso;
import fly.be.flyflix.conteudo.dto.CursoResumoDTO;
import fly.be.flyflix.conteudo.dto.curso.DetalhamentoCurso;
import fly.be.flyflix.conteudo.entity.Curso;
import fly.be.flyflix.conteudo.repository.CursoRepository;
import fly.be.flyflix.conteudo.service.CursoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private CursoService cursoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DetalhamentoCurso> cadastrar(@RequestBody @Valid CadastroCurso dados) {
        Curso curso = new Curso();
        curso.setTitulo(dados.titulo());
        curso.setDescricao(dados.descricao());
        curso.setImagemCapa(dados.imagemCapa());
        curso.setTags(dados.tags());
        curso.setAutorId(dados.autorId());

        try {
            curso.setNivel(Curso.NivelCurso.valueOf(dados.nivel().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        repository.save(curso);

        return ResponseEntity.created(URI.create("/api/cursos/" + curso.getId()))
                .body(new DetalhamentoCurso(curso));
    }

    @GetMapping
    public Page<DetalhamentoCurso> listar(@PageableDefault(size = 10, sort = "titulo") Pageable paginacao) {
        return repository.findAll(paginacao).map(DetalhamentoCurso::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoCurso> detalhar(@PathVariable Long id) {
        Optional<Curso> optional = repository.findById(id);
        return optional.map(curso -> ResponseEntity.ok(new DetalhamentoCurso(curso)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalhamentoCurso> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoCurso dados) {
        Optional<Curso> optional = repository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Curso curso = optional.get();
        curso.setTitulo(dados.titulo());
        curso.setDescricao(dados.descricao());
        curso.setImagemCapa(dados.imagemCapa());
        curso.setTags(dados.tags());
        curso.setAutorId(dados.autorId());

        try {
            curso.setNivel(Curso.NivelCurso.valueOf(dados.nivel().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new DetalhamentoCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recomendados")
    public ResponseEntity<List<CursoResumoDTO>> recomendarCursos(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(cursoService.getCursosRecomendados(usuarioId));
    }

    @GetMapping("/novos")
    public ResponseEntity<List<CursoResumoDTO>> cursosNovos() {
        return ResponseEntity.ok(cursoService.getCursosNovos());
    }

    @GetMapping("/populares")
    public ResponseEntity<List<CursoResumoDTO>> cursosPopulares() {
        return ResponseEntity.ok(cursoService.getCursosPopulares());
    }
}
