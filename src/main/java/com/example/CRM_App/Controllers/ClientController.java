package com.example.CRM_App.Controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CRM_App.Models.Client;
import com.example.CRM_App.Models.ClientDto;
import com.example.CRM_App.Repositories.ClientRepository;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;




@Controller
@RequestMapping("/clients")
public class ClientController {
 
	@Autowired
	private ClientRepository clientRepo;
	
	@GetMapping({"", "/"})
	public String getClient(Model model) {
	    List<Client> clients = clientRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
	    model.addAttribute("clients", clients);
	    return "clients/index";
	}
	
	@GetMapping({"/create"})
	public String createClient(Model model) {
	    ClientDto clientDto = new ClientDto(); 
	    model.addAttribute("clientDto", clientDto); 
	    return "clients/create";
	}
	
	@PostMapping("/create")
	public String createClient(
	    @Valid @ModelAttribute ClientDto clientDto,
	    BindingResult result
	) {

	    if (clientRepo.findByEmail(clientDto.getEmail()) != null) {
	        result.addError(
	            new FieldError("clientDto", "email", clientDto.getEmail(),
	                false, null, null, "Email address is already used")
	        );
	    }
	    

	    if (result.hasErrors()) {
	        return "clients/create";
	    }

	    Client client = new Client();
	    client.setFirstName(clientDto.getFirstName());
	    client.setLastName(clientDto.getLastName());
	    client.setEmail(clientDto.getEmail());
	    client.setPhone(clientDto.getPhone());
	    client.setAddress(clientDto.getAddress());
	    client.setStatus(clientDto.getStatus());
	    client.setCreateAt(new Date());

	    clientRepo.save(client);

	    return "redirect:/clients";
	}
	
	@GetMapping("/edit")
	public String editClient(Model model, @RequestParam int id) {
	    Client client = clientRepo.findById(id).orElse(null);

	    if (client == null) {
	        return "redirect:/clients";
	    }

	    ClientDto clientDto = new ClientDto();
	    clientDto.setFirstName(client.getFirstName());
	    clientDto.setLastName(client.getLastName());
	    clientDto.setEmail(client.getEmail());
	    clientDto.setPhone(client.getPhone());
	    clientDto.setAddress(client.getAddress());
	    clientDto.setStatus(client.getStatus());

	    model.addAttribute("client", client);
	    model.addAttribute("clientDto", clientDto);

	    return "clients/edit";
	}
	
	@PostMapping("/edit")
	public String editClient(
	    Model model,
	    @RequestParam int id,
	    @Valid @ModelAttribute ClientDto clientDto,
	    BindingResult result
	) {

	    if (result.hasErrors()) {
	        return "clients/edit";
	    }

	    Client client = clientRepo.findById(id).orElse(null);

	    if (client == null) {
	        return "redirect:/clients";
	    }

	    client.setFirstName(clientDto.getFirstName());
	    client.setLastName(clientDto.getLastName());
	    client.setEmail(clientDto.getEmail());
	    client.setPhone(clientDto.getPhone());
	    client.setAddress(clientDto.getAddress());
	    client.setStatus(clientDto.getStatus());

	    clientRepo.save(client);

	    return "redirect:/clients";
	}
	
	@GetMapping("/delete")
	public String deleteClient(@RequestParam int id) {
	    Client client = clientRepo.findById(id).orElse(null);

	    if (client != null) {
	        clientRepo.delete(client);
	    }

	    return "redirect:/clients";
	}


}
