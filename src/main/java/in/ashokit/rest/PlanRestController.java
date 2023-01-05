package in.ashokit.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import in.ashokit.constant.AppConstant;
import in.ashokit.entity.Plan;
import in.ashokit.props.AppProperties;
import in.ashokit.service.PlanService;

@RestController
public class PlanRestController {
	@Autowired
	private PlanService planService;

	private Map<String, String> message;

	// Parameterized Constructor
	public PlanRestController(PlanService planService, AppProperties appProbs) {
		this.planService = planService;
		this.message = appProbs.getMessage();
		System.out.println(this.message);
	}

	@GetMapping("/categories")
	public ResponseEntity<Map<Integer, String>> planCategory() {
		Map<Integer, String> categories = planService.getPlanCategory();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@PostMapping("/plan")
	public ResponseEntity<String> savePlan(@RequestBody Plan plan) {
		String responseMsg = AppConstant.EMPTY_STR; // String msg=""
		boolean savePlan = planService.savePlan(plan);
		if (savePlan) {
			responseMsg = message.get(AppConstant.PLAN_SAVE_SUCC);
		} else {
			responseMsg = message.get(AppConstant.PLAN_SAVE_FAILD);
		}
		return new ResponseEntity<>(responseMsg, HttpStatus.CREATED);
	}

	@GetMapping("/plans")
	public ResponseEntity<List<Plan>> plans() {
		List<Plan> allPlans = planService.getAllPlans();
		return new ResponseEntity<List<Plan>>(allPlans, HttpStatus.OK);
	}

	@GetMapping("/plan/{planId}")
	public ResponseEntity<Plan> getById(@PathVariable Integer planId) {
		Plan plan = planService.getPlanById(planId);
		return new ResponseEntity<>(plan, HttpStatus.OK);
	}

	@PutMapping("/plan")
	public ResponseEntity<String> updatePlan(Plan plan) {
		boolean isUpdate = planService.updatePlan(plan);
		String msg = AppConstant.EMPTY_STR;
		if (isUpdate) {
			msg = message.get(AppConstant.PLAN_UPDATE_SUCC);
		} else {
			msg = message.get(AppConstant.PLAN_UPDATE_SUCC);
		}
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@DeleteMapping("/plan/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable Integer planId) {
		boolean isDeleted = planService.deletePlanById(planId);
		String msg = AppConstant.EMPTY_STR;
		if (isDeleted) {
			msg = message.get(AppConstant.PLAN_DELETE_SUCC);
		} else {
			msg = message.get(AppConstant.PLAN_DELETE_FAILD);
		}
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@PutMapping("/status-change/{planId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable Integer planId, @PathVariable String status) {
		String msg = AppConstant.EMPTY_STR;
		boolean isStatusChanged = planService.planStatusChange(planId, status);
		if (isStatusChanged) {
			msg = message.get(AppConstant.PLAN_STATUS_CHANGED);
		} else {
			msg = message.get(AppConstant.PLAN_STATUS_CHANGED_FAILD);
		}
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
